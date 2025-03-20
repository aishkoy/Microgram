<#import "../layout.ftlh" as main>
<@main.layout>

    <div class="container py-5">
        <div class="row">
            <div class="col-md-6 offset-md-3">
                <h1 class="text-center">Upload Avatar</h1>
                <hr>
                <form action="/user/profile/upload" method="post" enctype="multipart/form-data">
                    <div class="mb-3">
                        <label for="avatar" class="form-label">Select File:</label>
                        <input type="file" class="form-control" id="avatar" name="avatar">
                    </div>
                    <button type="submit" class="btn btn-primary">Upload</button>
                </form>

                <div class="mt-5">
                    <p><strong>Allowed formats:</strong> JPG, JPEG, PNG</p>
                    <p><strong>Maximum size:</strong> 3 MB</p>
                </div>
            </div>
        </div>
    </div>

</@main.layout>