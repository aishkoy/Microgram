<#import "../layout.ftlh" as main>
<@main.layout>
    <div id="root">
        <div class="container">
            <div class="container">
                <form method="POST" enctype="multipart/form-data" action="/post">

                    <div class="form-group">
                        <div class="row">
                            <div class="col-3">
                            </div>
                            <div class="col-7 mt-3">
                                <div class="form-file">
                                    <label for="mFile" class="form-label">Select File:</label>
                                    <input type="file" class="form-control" id="mFile" name="mFile">
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="row">
                            <div class="col-3">
                            </div>
                            <div class="col-6">
                                <label for="content" class="form-label">Content:</label>
                                <input placeholder="Title" type="text" id="content" name="content" class="col form-control">
                            </div>

                        </div>
                    </div>

                    <div class="col-2 offset-3">
                        <button type="submit" class="btn-started btn btn-primary">Sumbit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</@main.layout>