import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { RouterModule } from "@angular/router";

/**
 * Footer component for the application.
 *
 * @author Chirag Singhal
 * @version 1.0.0
 */
@Component({
    selector: "app-footer",
    standalone: true,
    imports: [CommonModule, RouterModule],
    template: `
        <footer class="footer-custom mt-auto">
            <div class="container">
                <div class="row">
                    <div class="col-md-6 col-lg-4 mb-4">
                        <h5 class="text-white mb-3">
                            <i class="fas fa-shopping-cart me-2"></i>FreshMart
                        </h5>
                        <p class="text-light">
                            Your trusted partner for fresh groceries and quality
                            products. We deliver farm-fresh produce right to
                            your doorstep.
                        </p>
                        <div class="social-links">
                            <a
                                href="#"
                                class="text-light me-3"
                                aria-label="Facebook"
                            >
                                <i class="fab fa-facebook-f"></i>
                            </a>
                            <a
                                href="#"
                                class="text-light me-3"
                                aria-label="Twitter"
                            >
                                <i class="fab fa-twitter"></i>
                            </a>
                            <a
                                href="#"
                                class="text-light me-3"
                                aria-label="Instagram"
                            >
                                <i class="fab fa-instagram"></i>
                            </a>
                            <a
                                href="#"
                                class="text-light"
                                aria-label="LinkedIn"
                            >
                                <i class="fab fa-linkedin-in"></i>
                            </a>
                        </div>
                    </div>

                    <div class="col-md-6 col-lg-2 mb-4">
                        <h6 class="text-white mb-3">Quick Links</h6>
                        <ul class="list-unstyled">
                            <li class="mb-2">
                                <a
                                    routerLink="/"
                                    class="text-light text-decoration-none"
                                >
                                    <i class="fas fa-home me-2"></i>Home
                                </a>
                            </li>
                            <li class="mb-2">
                                <a
                                    routerLink="/products"
                                    class="text-light text-decoration-none"
                                >
                                    <i class="fas fa-shopping-basket me-2"></i
                                    >Products
                                </a>
                            </li>
                            <li class="mb-2">
                                <a
                                    routerLink="/products/search"
                                    class="text-light text-decoration-none"
                                >
                                    <i class="fas fa-search me-2"></i>Search
                                </a>
                            </li>
                            <li class="mb-2">
                                <a
                                    routerLink="/register"
                                    class="text-light text-decoration-none"
                                >
                                    <i class="fas fa-user-plus me-2"></i
                                    >Register
                                </a>
                            </li>
                        </ul>
                    </div>

                    <div class="col-md-6 col-lg-3 mb-4">
                        <h6 class="text-white mb-3">Categories</h6>
                        <ul class="list-unstyled">
                            <li class="mb-2">
                                <a
                                    href="#"
                                    class="text-light text-decoration-none"
                                >
                                    <i class="fas fa-apple-alt me-2"></i>Fruits
                                </a>
                            </li>
                            <li class="mb-2">
                                <a
                                    href="#"
                                    class="text-light text-decoration-none"
                                >
                                    <i class="fas fa-carrot me-2"></i>Vegetables
                                </a>
                            </li>
                            <li class="mb-2">
                                <a
                                    href="#"
                                    class="text-light text-decoration-none"
                                >
                                    <i class="fas fa-cheese me-2"></i>Dairy
                                </a>
                            </li>
                            <li class="mb-2">
                                <a
                                    href="#"
                                    class="text-light text-decoration-none"
                                >
                                    <i class="fas fa-bread-slice me-2"></i
                                    >Bakery
                                </a>
                            </li>
                        </ul>
                    </div>

                    <div class="col-md-6 col-lg-3 mb-4">
                        <h6 class="text-white mb-3">Contact Info</h6>
                        <ul class="list-unstyled">
                            <li class="mb-2 text-light">
                                <i class="fas fa-map-marker-alt me-2"></i>
                                123 Grocery Street, Food City, FC 12345
                            </li>
                            <li class="mb-2 text-light">
                                <i class="fas fa-phone me-2"></i>
                                +1 (555) 123-4567
                            </li>
                            <li class="mb-2 text-light">
                                <i class="fas fa-envelope me-2"></i>
                                info&#64;freshmart.com
                            </li>
                            <li class="mb-2 text-light">
                                <i class="fas fa-clock me-2"></i>
                                24/7 Customer Support
                            </li>
                        </ul>
                    </div>
                </div>

                <hr class="my-4 bg-light" />

                <div class="row align-items-center">
                    <div class="col-md-6">
                        <p class="text-light mb-0">
                            &copy; {{ currentYear }} Online Grocery Ordering
                            System. All rights reserved.
                        </p>
                    </div>
                    <div class="col-md-6 text-md-end">
                        <p class="text-light mb-0">
                            Developed with
                            <i class="fas fa-heart text-danger"></i> by
                            <strong>Chirag Singhal</strong>
                        </p>
                    </div>
                </div>

                <div class="row mt-3">
                    <div class="col-12 text-center">
                        <small class="text-light">
                            <a
                                href="#"
                                class="text-light text-decoration-none me-3"
                                >Privacy Policy</a
                            >
                            <a
                                href="#"
                                class="text-light text-decoration-none me-3"
                                >Terms of Service</a
                            >
                            <a href="#" class="text-light text-decoration-none"
                                >Cookie Policy</a
                            >
                        </small>
                    </div>
                </div>
            </div>
        </footer>
    `,
    styles: [
        `
            .footer-custom {
                background: #343a40;
                color: white;
                padding: 3rem 0 1rem;
            }

            .social-links a {
                display: inline-block;
                width: 40px;
                height: 40px;
                line-height: 40px;
                text-align: center;
                border-radius: 50%;
                background: rgba(255, 255, 255, 0.1);
                transition: all 0.3s ease;
            }

            .social-links a:hover {
                background: rgba(255, 255, 255, 0.2);
                transform: translateY(-2px);
            }

            .list-unstyled a {
                transition: color 0.3s ease;
            }

            .list-unstyled a:hover {
                color: #667eea !important;
            }

            hr {
                opacity: 0.3;
            }

            @media (max-width: 768px) {
                .footer-custom {
                    padding: 2rem 0 1rem;
                }

                .col-md-6.text-md-end {
                    text-align: center !important;
                    margin-top: 1rem;
                }
            }
        `,
    ],
})
export class FooterComponent {
    currentYear = new Date().getFullYear();
}
