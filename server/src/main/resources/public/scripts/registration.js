// Регистрация
const notificationField = document.getElementById('notification');
notificationField.textContent = '';

let login = document.getElementById('userLogin');
let password = document.getElementById('userPassword');
let confirmPassword = document.getElementById('confirmPassword');

const button = document.getElementById('registrationButton');
button.addEventListener('click', () => {

    let isValid = true;

    if (!login.value || !password.value) {
        isValid = false;
        notificationField.innerHTML = '<p>Необходимо заполнить все поля!</p>';
    }

    if (password.value != confirmPassword.value) {
        isValid = false;
        notificationField.innerHTML = '<p>Введенные пароли не совпадают!</p>';
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
                notificationField.innerHTML = '<p>Указанный вами логин уже занят!</p>';
            }
            else {
                notificationField.innerHTML = `<p>Создан аккаунт - ${document.getElementById('userLogin').value}</p>`;
            }
        })
        .catch(error => {
            console.log("Error:", error);
        });
    }
});