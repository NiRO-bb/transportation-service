// Вернуться назад
const exitButton = document.getElementById('exitButton');
exitButton.addEventListener('click', () => {
    window.location.href = '/index.html';
});

// Регистрация
const notification = document.getElementById('notification');
notification.textContent = '';

let login = document.getElementById('userLogin');
let password = document.getElementById('userPassword');
let confirmPassword = document.getElementById('confirmPassword');

const button = document.getElementById('registrationButton');
button.addEventListener('click', () => {

    let isValid = true;

    if (!login.value || !password.value) {
        isValid = false;
        notification.textContent = 'Необходимо заполнить все поля!';
    }

    if (password.value != confirmPassword.value) {
        isValid = false;
        notification.textContent = 'Введенные пароли не совпадают!';
    }

    if (isValid) {

        // данные в формате JSON
        const data = {
            login: login.value,
            password: password.value
        }

        // HTTP-запрос - регистрация
        fetch('http://localhost:8080/sign_up', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            return response.json();
        })
        .then(data => {
            if (!data) {
                notification.textContent = 'Указанный вами логин уже занят!';
            }
            else {
                notification.textContent = `Создан аккаунт - ${document.getElementById('userLogin').value}`;
            }
        })
        .catch(error => {
            console.log("Error:", error);
        });
    }
});