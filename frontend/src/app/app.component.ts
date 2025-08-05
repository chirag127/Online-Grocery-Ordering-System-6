import { Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterOutlet, Router, NavigationEnd } from "@angular/router";
import { filter } from "rxjs/operators";

import { NavbarComponent } from "./components/shared/navbar/navbar.component";
import { FooterComponent } from "./components/shared/footer/footer.component";
import { AuthService } from "./services/auth.service";

/**
 * Root component for the Online Grocery Ordering System.
 *
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Component({
    selector: "app-root",
    standalone: true,
    imports: [CommonModule, RouterOutlet, NavbarComponent, FooterComponent],
    template: `
        <div class="app-container d-flex flex-column min-vh-100">
            <app-navbar></app-navbar>

            <main class="flex-grow-1">
                <router-outlet></router-outlet>
            </main>

            <app-footer></app-footer>
        </div>
    `,
    styles: [
        `
            .app-container {
                min-height: 100vh;
            }

            main {
                padding-top: 0;
            }

            @media (max-width: 768px) {
                main {
                    padding-top: 0;
                }
            }
        `,
    ],
})
export class AppComponent implements OnInit {
    title = "Online Grocery Ordering System";

    constructor(private router: Router, private authService: AuthService) {}

    ngOnInit(): void {
        // Track route changes for analytics or other purposes
        this.router.events
            .pipe(filter((event) => event instanceof NavigationEnd))
            .subscribe((event) => {
                // You can add analytics tracking here
                if (event instanceof NavigationEnd) {
                    console.log("Navigation to:", event.url);
                }
            });

        // Validate token on app initialization
        this.authService.validateToken().subscribe((isValid) => {
            if (!isValid && this.authService.getToken()) {
                // Token is invalid, redirect to login
                this.router.navigate(["/login"]);
            }
        });
    }
}
