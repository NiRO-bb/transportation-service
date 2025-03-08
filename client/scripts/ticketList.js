const textField = document.getElementById('response');
textField.textContent = '';

const button = document.getElementById('searchButton');
button.addEventListener('click', () => {

    const login = document.getElementById('userLogin');
    const password = document.getElementById('userPassword');
    
    let isValid = true;

    if (!login.value || !password.value) {
        isValid = false;
        textField.textContent = 'Необходимо заполнить все поля!';
    }

    if (isValid) {

        const data = {
            login: login.value,
            password: password.value
        }

        // HTTP-запрос - авторизация
        fetch('http://localhost:8080/sign_in', {
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
                textField.textContent = 'Вы неверно указали логин и/или пароль!';
            }
            else {
                // HTTP-запрос - список забронированных билетов пользователя
                fetch(`http://localhost:8080/ticket/list?userLogin=${document.getElementById('userLogin').value}`)
                .then(response => {
                    return response.json();
                })
                .then(data => {
                    let result = '';
                    
                    if (data) {
                        data.forEach(ticket => {
                            result += `<p>Номер билета: ${ticket.id}.</p>`;
                            result += `<p>Маршрут: ${ticket.route}</p>`;
                        });
                    }

                    if (!result) {
                        result += '<p>У Вас нет забронированных билетов.</p>';
                    }

                    textField.innerHTML = '<p><h2>Ваши билеты</h2></p>' + result;
                });
            }
        })
        .catch(error => {
            console.log("Error:", error);
        });
    }
});