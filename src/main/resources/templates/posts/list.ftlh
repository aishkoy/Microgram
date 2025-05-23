<#import "../layout.ftlh" as layout>
<@layout.page title="Все посты" styles="" activeNav="explore">

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2 class="fw-bold">Все посты</h2>
            <a href="/posts/create" class="btn btn-outline-primary">
                <i class="bi bi-plus-circle"></i> Новый пост
            </a>
        </div>

        <#if successMessage??>
            <div class="alert alert-success mb-4 shadow-sm">
                ${successMessage}
            </div>
        </#if>

        <#if errorMessage??>
            <div class="alert alert-danger mb-4 shadow-sm">
                ${errorMessage}
            </div>
        </#if>

        <div class="row g-4">
            <#if posts?? && posts?size gt 0>
                <#list posts as post>
                    <div class="col-lg-4 col-md-6">
                        <a href="/posts/${post.id}" class="text-decoration-none">
                            <div class="card post-card shadow-sm border-0">
                                <div class="ratio ratio-4x3">
                                    <img src="/api/posts/${post.id}/image"
                                         class="card-img-top rounded"
                                         alt="Пост"
                                         style="object-fit: cover;">
                                </div>
                                <div class="post-overlay">
                                    <div class="post-info">
                                        <span><i class="bi bi-heart-fill me-1"></i> ${post.likes!"0"}</span>
                                        <span><i class="bi bi-chat-fill me-1"></i> ${post.comments!"0"}</span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                </#list>
            <#else>
                <div class="text-center mt-5">
                    <p class="fs-5">Пока нет ни одного поста</p>
                    <a href="/posts/create" class="btn btn-primary">Создать пост</a>
                </div>
            </#if>
        </div>
    </div>

    <style>
        .post-card {
            position: relative;
            overflow: hidden;
            border-radius: 12px;
            transition: transform 0.2s ease, box-shadow 0.2s ease;
        }

        .post-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
        }

        .post-overlay {
            position: absolute;
            bottom: 0;
            left: 0;
            width: 100%;
            padding: 0.5rem 1rem;
            background: linear-gradient(to top, rgba(0, 0, 0, 0.6), transparent);
            color: white;
            display: flex;
            justify-content: space-between;
            align-items: center;
            opacity: 1;
            transition: opacity 0.3s ease-in-out;
        }

        .post-info span {
            display: flex;
            align-items: center;
            gap: 4px;
            font-weight: 500;
        }

        @media (max-width: 576px) {
            .post-info {
                font-size: 0.9rem;
            }
        }
    </style>

</@layout.page>
