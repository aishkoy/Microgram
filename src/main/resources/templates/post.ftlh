<#import "layout.ftlh" as main>
<@main.layout>
    <div class="max-w-2xl mx-auto px-4 py-8">
        <div class="bg-white p-6 rounded-lg shadow-lg">
            <h2 class="text-2xl font-semibold text-gray-800 mb-6 flex items-center gap-2">
                <i data-lucide="file-plus" class="text-amber-500"></i>
                Create a New Post
            </h2>

            <form method="POST" enctype="multipart/form-data" action="/posts" class="space-y-6">
                <!-- File Upload Field -->
                <div class="space-y-2">
                    <label for="mFile" class="block text-sm font-medium text-gray-700 flex items-center gap-2">
                        <i data-lucide="upload" class="text-gray-500"></i>
                        Select File:
                    </label>
                    <input type="file"
                           class="hidden"
                           id="mFile"
                           name="mFile"
                           onchange="previewImage(this, {
           previewContainerId: 'filePreview',
           previewImageId: 'previewImage'
       })">
                    <label for="mFile" class="cursor-pointer block w-full px-4 py-2 text-sm text-gray-700 border border-gray-300 rounded-md bg-amber-50 hover:bg-amber-100 transition-colors flex items-center gap-2">
                        <i data-lucide="image" class="text-gray-500"></i>
                        Choose a file
                    </label>
                    <div id="filePreview" class="mt-2 hidden">
                        <img id="previewImage" class="max-w-full h-40 object-cover rounded-md border border-gray-300">
                    </div>
                </div>

                <!-- Content Field -->
                <div class="space-y-2">
                    <label for="content" class="block text-sm font-medium text-gray-700 flex items-center gap-2">
                        <i data-lucide="type" class="text-gray-500"></i>
                        Title:
                    </label>
                    <textarea placeholder="Write something amazing..."
                              id="content"
                              name="content"
                              rows="4"
                              class="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-amber-500 focus:border-amber-500 sm:text-sm resize-none"></textarea>
                </div>

                <!-- Submit Button -->
                <div class="pt-2">
                    <button type="submit"
                            class="w-full flex items-center justify-center gap-2 px-4 py-2 bg-amber-500 text-white font-semibold rounded-md hover:bg-amber-600 transition-all duration-300 shadow-md">
                        <i data-lucide="send"></i>
                        Publish
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script src="/js/imagePreview.js"></script>
</@main.layout>
