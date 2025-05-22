<#import "../layout.ftlh" as layout>

<@layout.page title="Создание поста" styles="" activeNav="create">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card shadow">
                    <div class="card-header bg-white">
                        <h4 class="mb-0">Создать новый пост</h4>
                    </div>
                    <div class="card-body">
                        <#if errorMessage??>
                            <div class="alert alert-danger mb-4">
                                ${errorMessage}
                            </div>
                        </#if>

                        <form action="/posts/create" method="post" enctype="multipart/form-data">
                            <#if _csrf??>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            </#if>
                            <div class="mb-4">
                                <label for="image" class="form-label">Изображение</label>
                                <div class="mb-3">
                                    <img id="imagePreview" src="/static/images/placeholder.jpg"
                                         class="img-fluid rounded" style="max-height: 300px; display: none;">
                                </div>
                                <input type="file" class="form-control" id="image" name="image" accept="image/*" required>
                                <small class="text-muted">Выберите изображение для публикации</small>
                            </div>

                            <div class="mb-4">
                                <label for="description" class="form-label">Описание</label>
                                <textarea class="form-control" id="description" name="description" rows="4"
                                          placeholder="Добавьте описание к вашему посту..."></textarea>
                            </div>

                            <div class="d-grid">
                                <button type="submit" class="btn btn-primary">Опубликовать</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.getElementById('image').addEventListener('change', function(event) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    const preview = document.getElementById('imagePreview');
                    preview.src = e.target.result;
                    preview.style.display = 'block';
                }
                reader.readAsDataURL(file);
            }
        });
    </script>
</@layout.page>