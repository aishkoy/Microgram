function previewImage(input, options = {}) {
    const defaults = {
        previewContainerSelector: null,
        previewImageSelector: null,
        previewContainerId: 'filePreview',
        previewImageId: 'previewImage',
        iconSelector: '[data-lucide="user"]',
        createImageIfMissing: true,
        showContainer: true,
        imageClasses: 'w-full h-full object-cover'
    };

    // Merge options
    const config = {...defaults, ...options};

    if (input.files && input.files[0]) {
        const reader = new FileReader();

        reader.onload = function(e) {
            let previewContainer;

            if (config.previewContainerSelector) {
                previewContainer = document.querySelector(config.previewContainerSelector);
            } else if (config.previewContainerId) {
                previewContainer = document.getElementById(config.previewContainerId);

                if (config.showContainer && previewContainer) {
                    previewContainer.classList.remove('hidden');
                }
            }

            if (!previewContainer) return;

            let previewImg;

            if (config.previewImageSelector) {
                previewImg = document.querySelector(config.previewImageSelector);
            } else if (config.previewImageId) {
                previewImg = document.getElementById(config.previewImageId);
            }

            if (!previewImg && config.createImageIfMissing) {
                if (config.iconSelector) {
                    const icon = previewContainer.querySelector(config.iconSelector);
                    if (icon) icon.remove();
                }

                previewImg = document.createElement('img');
                previewImg.className = config.imageClasses;
                if (config.previewImageId) {
                    previewImg.id = config.previewImageId;
                }
                previewContainer.appendChild(previewImg);
            }

            if (previewImg) {
                previewImg.src = e.target.result;
            }
        };

        reader.readAsDataURL(input.files[0]);
    }
}