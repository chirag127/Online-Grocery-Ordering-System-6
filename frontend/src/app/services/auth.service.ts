import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { BehaviorSubject, Observable, throwError, of } from "rxjs";
import { map, catchError } from "rxjs/operators";
import {
    LoginRequest,
    AuthResponse,
    User,
    UserRole,
} from "../models/auth.model";
import { CustomerRegistration } from "../models/customer.model";

/**
 * Authentication service for handling user login, logout, and token management.
 *
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Injectable({
    providedIn: "root",
})
export class AuthService {
    private readonly API_URL = "http://localhost:8080/api/auth";
    private readonly TOKEN_KEY = "token";
    private readonly USER_KEY = "user";

    private currentUserSubject = new BehaviorSubject<User | null>(null);
    public currentUser$ = this.currentUserSubject.asObservable();

    private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
    public isAuthenticated$ = this.isAuthenticatedSubject.asObservable();

    constructor(private http: HttpClient) {
        this.loadUserFromStorage();
    }

    /**
     * Login user with credentials.
     */
    login(credentials: LoginRequest): Observable<AuthResponse> {
        return this.http
            .post<AuthResponse>(`${this.API_URL}/login`, credentials)
            .pipe(
                map((response) => {
                    if (response.success || response.token) {
                        this.setSession(response);
                    }
                    return response;
                }),
                catchError(this.handleError)
            );
    }

    /**
     * Login admin user with credentials.
     */
    adminLogin(credentials: LoginRequest): Observable<AuthResponse> {
        return this.http
            .post<AuthResponse>(`${this.API_URL}/admin/login`, credentials)
            .pipe(
                map((response) => {
                    if (response.success || response.token) {
                        this.setSession(response);
                    }
                    return response;
                }),
                catchError(this.handleError)
            );
    }

    /**
     * Register new customer.
     */
    register(registration: CustomerRegistration): Observable<any> {
        return this.http
            .post(`${this.API_URL}/register`, registration)
            .pipe(catchError(this.handleError));
    }

    /**
     * Logout current user.
     */
    logout(): Observable<any> {
        return this.http.post(`${this.API_URL}/logout`, {}).pipe(
            map(() => {
                this.clearSession();
                return { success: true, message: "Logged out successfully" };
            }),
            catchError(() => {
                this.clearSession();
                return of({
                    success: true,
                    message: "Logged out successfully",
                });
            })
        );
    }

    /**
     * Get current user.
     */
    getCurrentUser(): User | null {
        return this.currentUserSubject.value;
    }

    /**
     * Check if user is authenticated.
     */
    isAuthenticated(): boolean {
        return this.isAuthenticatedSubject.value;
    }

    /**
     * Check if user has admin role.
     */
    isAdmin(): boolean {
        const user = this.getCurrentUser();
        return (
            user?.role === UserRole.ADMIN || user?.role === UserRole.SUPER_ADMIN
        );
    }

    /**
     * Check if user has customer role.
     */
    isCustomer(): boolean {
        const user = this.getCurrentUser();
        return user?.role === UserRole.CUSTOMER;
    }

    /**
     * Get authentication token.
     */
    getToken(): string | null {
        return localStorage.getItem(this.TOKEN_KEY);
    }

    /**
     * Get HTTP headers with authorization token.
     */
    getAuthHeaders(): HttpHeaders {
        const token = this.getToken();
        return new HttpHeaders({
            "Content-Type": "application/json",
            Authorization: token ? `Bearer ${token}` : "",
        });
    }

    /**
     * Validate token with server.
     */
    validateToken(): Observable<boolean> {
        const token = this.getToken();
        if (!token) {
            return new Observable((observer) => {
                observer.next(false);
                observer.complete();
            });
        }

        return this.http.post<any>(`${this.API_URL}/validate`, { token }).pipe(
            map((response) => response.valid),
            catchError(() => {
                this.clearSession();
                return of(false);
            })
        );
    }

    /**
     * Set authentication session.
     */
    private setSession(authResponse: AuthResponse): void {
        if (authResponse.token) {
            localStorage.setItem(this.TOKEN_KEY, authResponse.token);
        }

        const user: User = {
            id: authResponse.id!,
            username: authResponse.username!,
            email: authResponse.email!,
            role: authResponse.role as UserRole,
            isActive: true,
        };

        localStorage.setItem(this.USER_KEY, JSON.stringify(user));
        this.currentUserSubject.next(user);
        this.isAuthenticatedSubject.next(true);
    }

    /**
     * Clear authentication session.
     */
    private clearSession(): void {
        localStorage.removeItem(this.TOKEN_KEY);
        localStorage.removeItem(this.USER_KEY);
        this.currentUserSubject.next(null);
        this.isAuthenticatedSubject.next(false);
    }

    /**
     * Load user from local storage.
     */
    private loadUserFromStorage(): void {
        const token = localStorage.getItem(this.TOKEN_KEY);
        const userStr = localStorage.getItem(this.USER_KEY);

        if (token && userStr) {
            try {
                const user = JSON.parse(userStr);
                this.currentUserSubject.next(user);
                this.isAuthenticatedSubject.next(true);
            } catch (error) {
                this.clearSession();
            }
        }
    }

    /**
     * Handle HTTP errors.
     */
    private handleError(error: any): Observable<never> {
        let errorMessage = "An error occurred";

        if (error.error?.message) {
            errorMessage = error.error.message;
        } else if (error.message) {
            errorMessage = error.message;
        }

        return throwError(() => new Error(errorMessage));
    }
}
