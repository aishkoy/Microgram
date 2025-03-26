<#import "layout.ftlh" as main>
<@main.layout>
    <div class="max-w-2xl mx-auto px-4 py-8">
        <div class="bg-white p-6 rounded-lg shadow-lg">
            <h2 class="text-2xl font-semibold text-gray-800 mb-6 flex items-center gap-2">
                <i data-lucide="user" class="text-amber-500"></i>
                Upload Avatar
            </h2>

            <form action="/profile/upload" method="post" enctype="multipart/form-data" class="space-y-6">
                <!-- File Upload Field -->
                <div class="space-y-2">
                    <label for="avatar" class="block text-sm font-medium text-gray-700 flex items-center gap-2">
                        <i data-lucide="upload" class="text-gray-500"></i>
                        Select Image:
                    </label>
                    <input type="file"
                           class="hidden"
                           id="avatar"
                           name="avatar"
                           onchange="previewImage(this, {
                               previewContainerId: 'filePreview',
                               previewImageId: 'previewImage'
                           })">
                    <label for="avatar"
                           class="cursor-pointer block w-full px-4 py-2 text-sm text-gray-700 border border-gray-300 rounded-md bg-amber-50 hover:bg-amber-100 transition-colors flex items-center gap-2">
                        <i data-lucide="image" class="text-gray-500"></i>
                        Choose an avatar image
                    </label>
                    <div id="filePreview" class="mt-2 hidden">
                        <img id="previewImage"
                             class="max-w-full h-40 object-cover rounded-full border border-gray-300 mx-auto">
                    </div>
                </div>

                <!-- Information Box -->
                <div class="bg-gray-50 p-4 rounded-md border border-gray-200 space-y-2">
                    <p class="text-sm text-gray-700 flex items-center gap-2">
                        <i data-lucide="check-circle" class="text-amber-500"></i>
                        <span class="font-semibold">Allowed formats:</span> JPG, JPEG, PNG
                    </p>
                    <p class="text-sm text-gray-700 flex items-center gap-2">
                        <i data-lucide="info" class="text-amber-500"></i>
                        <span class="font-semibold">Maximum size:</span> 3 MB
                    </p>
                </div>

                <!-- Action Buttons -->
                <div class="pt-2 flex gap-4">
                    <button type="submit"
                            class="flex-1 flex items-center justify-center gap-2 px-4 py-2 bg-amber-500 text-white font-semibold rounded-md hover:bg-amber-600 transition-all duration-300 shadow-md">
                        <i data-lucide="upload-cloud"></i>
                        Upload
                    </button>

                    <a href="/"
                       class="flex-1 flex items-center justify-center gap-2 px-4 py-2 bg-gray-200 text-gray-700 font-semibold rounded-md hover:bg-gray-300 transition-all duration-300 shadow-md">
                        <i data-lucide="x"></i>
                        Cancel
                    </a>
                </div>
            </form>
        </div>
    </div>

    <script src="/js/imagePreview.js"></script>
</@main.layout>