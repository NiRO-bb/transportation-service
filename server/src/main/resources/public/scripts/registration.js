// Регистрация
const notificationField = document.getElementById('notification');
notificationField.textContent = '';

let login = document.getElementById('userLogin');
let password = document.getElementById('userPassword');
let confirmPassword = document.getElementById('confirmPassword');

const button = document.getElementById('registrationButton');
button.addEventListener('click', () => {

    let isValid = true;

    if (password.value != confirmPassword.value) {
        isValid = false;
        notificationField.innerHTML = '<p>Введенные пароли не совпадают!</p>'; // проврерка на сервере - дублировать
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
                if (!response.ok) {
                    return response.json()
                        .then(error => {
                            throw {
                                message: error.message,
                                status: error.status
                            };
                        });
                }
                return response.json();
            })
            .then(() => {
                notificationField.innerHTML = `<p>Создан аккаунт - ${document.getElementById('userLogin').value}</p>`;
            })
            .catch(error => {
                notificationField.innerHTML = '';
                error.message.forEach(msg => {
                    console.log("Error:", msg);
                    notificationField.innerHTML += `<p>${msg}</p>`;
                });
            });
    }
});