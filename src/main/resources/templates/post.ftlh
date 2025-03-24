<#import "layout.ftlh" as main>
<@main.layout>
    <div class="max-w-2xl mx-auto px-4 py-8">
        <form method="POST" enctype="multipart/form-data" action="/post" class="space-y-6">
            <!-- File Upload Field -->
            <div class="space-y-2">
                <label for="mFile" class="block text-sm font-medium text-gray-700">Select File:</label>
                <input type="file"
                       class="block w-full text-sm text-gray-500
                              file:mr-4 file:py-2 file:px-4
                              file:rounded-md file:border-0
                              file:text-sm file:font-semibold
                              file:bg-amber-50 file:text-gray-700
                              hover:file:bg-amber-100"
                       id="mFile"
                       name="mFile">
            </div>

            <!-- Content Field -->
            <div class="space-y-2">
                <label for="content" class="block text-sm font-medium text-gray-700">Content:</label>
                <input placeholder="Title"
                       type="text"
                       id="content"
                       name="content"
                       class="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm placeholder-gray-400 focus:outline-none focus:ring-amber-500 focus:border-amber-500 sm:text-sm">
            </div>

            <!-- Submit Button -->
            <div class="pt-2">
                <button type="submit"
                        class="bg-amber-50 text-gray-700 px-4 py-2 border rounded-md hover:text-black hover:bg-amber-100 transition-colors">
                    Submit
                </button>
            </div>
        </form>
    </div>
</@main.layout>