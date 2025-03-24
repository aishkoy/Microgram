<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Edit Profile</title>
    <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    <script src="https://unpkg.com/lucide@latest"></script>
</head>
<body class="bg-gray-50 min-h-screen">

<div class="container max-w-3xl mx-auto px-4 py-8">
    <h1 class="text-2xl font-bold mb-6 text-center">Edit Profile</h1>

    <div class="bg-white border border-gray-200 rounded-lg shadow-sm p-6 mb-6">
        <!-- Avatar Upload Section -->
        <div class="mb-8">
            <h2 class="text-xl font-medium mb-4">Profile Picture</h2>

            <div class="flex flex-col md:flex-row items-center gap-6">
                <div class="w-32 h-32 rounded-full border border-gray-300 overflow-hidden flex items-center justify-center bg-gray-100">
                    <#if profile.avatar??>
                        <img src="/api/post/${profile.avatar}" alt="Current avatar" class="w-full h-full object-cover" />
                    <#else>
                        <i data-lucide="user" class="w-16 h-16 text-gray-400"></i>
                    </#if>
                </div>

                <div class="flex-grow">
                    <form action="/user/profile/upload" method="post" enctype="multipart/form-data" class="space-y-4">
                        <div class="flex flex-col">
                            <label for="avatar" class="mb-2 text-sm font-medium text-gray-700">Select new profile picture:</label>
                            <input
                                    type="file"
                                    id="avatar"
                                    name="avatar"
                                    accept="image/jpeg,image/png,image/jpg"
                                    class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                                    onchange="previewImage(this)"
                            />
                        </div>

                        <div>
                            <button
                                    type="submit"
                                    class="bg-amber-50 text-gray-700 px-4 py-2 border rounded-2xl cursor-pointer hover:text-black hover:bg-amber-100 transition-colors"
                            >
                                Upload
                            </button>
                        </div>

                        <div class="text-sm text-gray-500">
                            <p><strong>Allowed formats:</strong> JPG, JPEG, PNG</p>
                            <p><strong>Maximum size:</strong> 3 MB</p>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <hr class="my-6 border-gray-200" />

        <!-- Profile Information Section -->
        <div>
            <h2 class="text-xl font-medium mb-4">Personal Information</h2>

            <form action="/user/profile/edit" method="post" class="space-y-4">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                    <div>
                        <label for="name" class="block text-sm font-medium text-gray-700 mb-1">Name</label>
                        <input
                                type="text"
                                id="name"
                                name="name"
                                value="${profile.name!''}"
                                class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>
                    <div>
                        <label for="surname" class="block text-sm font-medium text-gray-700 mb-1">Surname</label>
                        <input
                                type="text"
                                id="surname"
                                name="surname"
                                value="${profile.surname!''}"
                                class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                        />
                    </div>
                </div>

                <div>
                    <label for="username" class="block text-sm font-medium text-gray-700 mb-1">Username</label>
                    <input
                            type="text"
                            id="username"
                            name="username"
                            value="${profile.username!''}"
                            class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                    />
                </div>

                <div>
                    <label for="gender" class="block text-sm font-medium text-gray-700 mb-1">Gender</label>
                    <select
                            id="gender"
                            name="gender"
                            class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                        <option value="male" <#if profile.gender?? && profile.gender == 'male'>selected</#if>>Male</option>
                        <option value="female" <#if profile.gender?? && profile.gender == 'female'>selected</#if>>Female</option>
                        <option value="other" <#if profile.gender?? && profile.gender == 'other'>selected</#if>>Other</option>
                    </select>
                </div>

                <div>
                    <label for="aboutMe" class="block text-sm font-medium text-gray-700 mb-1">About Me</label>
                    <textarea
                            id="aboutMe"
                            name="aboutMe"
                            rows="4"
                            class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:ring-2 focus:ring-blue-500"
                    ><#if profile.aboutMe??>${profile.aboutMe}</#if></textarea>
                </div>

                <div>
                    <button
                            type="submit"
                            class="bg-amber-50 text-gray-700 px-6 py-2 border rounded-2xl cursor-pointer hover:text-black hover:bg-amber-100 transition-colors"
                    >
                        Save Changes
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    // Initialize Lucide icons
    lucide.createIcons();

    // Preview image before upload
    function previewImage(input) {
        if (input.files && input.files[0]) {
            const reader = new FileReader();

            reader.onload = function(e) {
                // Find the parent div that contains the preview image or icon
                const previewContainer = document.querySelector('.rounded-full');

                // Check if there's already an img element
                let previewImg = previewContainer.querySelector('img');

                if (!previewImg) {
                    // If there's an icon, remove it
                    const icon = previewContainer.querySelector('[data-lucide="user"]');
                    if (icon) icon.remove();

                    // Create new image element
                    previewImg = document.createElement('img');
                    previewImg.className = 'w-full h-full object-cover';
                    previewContainer.appendChild(previewImg);
                }

                // Set the source to the loaded file
                previewImg.src = e.target.result;
            }

            reader.readAsDataURL(input.files[0]);
        }
    }
</script>
</body>
</html>