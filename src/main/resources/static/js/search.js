const searchInput = document.getElementById('user-search');
const resultsContainer = document.getElementById('results');
let timer;

searchInput.addEventListener('input', () => {
    const query = searchInput.value.trim();

    clearTimeout(timer);
    if (query.length === 0) {
        resultsContainer.innerHTML = '';
        return;
    }

    timer = setTimeout(() => {
        fetchUsers(query);
    }, 300);
});

async function fetchUsers(query) {
    try {
        const response = await fetch(`/api/users/search?q=${encodeURIComponent(query)}`);

        if (!response.ok) {
            console.log(response.status);
            if (response.status === 404) {
                displayMessage('Ничего не найдено', 'text-warning');
                return;
            }
            throw new Error('Ошибка при получении данных');
        }

        const data = await response.json();
        displayResults(data);
    } catch (error) {
        displayMessage('Ошибка загрузки', 'text-danger');
        console.error(error);
    }
}

function displayResults(users) {
    resultsContainer.innerHTML = '';

    users.forEach(user => {
        const link = document.createElement('a');
        link.href = `/users/${user.id}`;
        link.className = 'list-group-item list-group-item-action';
        link.textContent = user.username || `Пользователь #${user.id}`;
        resultsContainer.appendChild(link);
    });
}

function displayMessage(text, className = '') {
    resultsContainer.innerHTML = `<div class="list-group-item ${className}">${text}</div>`;
}
