// Вернуться назад
const exitButton = document.getElementById('exitButton');
exitButton.addEventListener('click', () => {
    window.location.href = '/index.html';
});

// Получить список билетов
const textField = document.getElementById('response');
textField.textContent = '';

const button = document.getElementById('searchButton');
button.addEventListener('click', buttonFunction);

function buttonFunction() {
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
                    displayTickets();
                }
            })
            .catch(error => {
                console.log("Error:", error);
            });
    }
}

async function displayTickets() {
    textField.innerHTML = '<p><h2>Ваши билеты</h2></p>';

    try {
        // HTTP-запрос - список забронированных билетов пользователя
        const ticketsResponse = await fetch(`http://localhost:8080/ticket/list?userLogin=${document.getElementById('userLogin').value}`);
        const ticketsData = await ticketsResponse.json();

        // HTTP-запрос - информация о маршруте по id
        const routesResponse = await fetch(`http://localhost:8080/search/route_list?userLogin=${document.getElementById('userLogin').value}`)
        const routesData = await routesResponse.json();

        if (ticketsData.length === 0) {
            textField.innerHTML += '<p>У Вас нет забронированных билетов.</p>';
            return;
        }

        for (const ticket of ticketsData) {
            const route = routesData.find(route => route.id === ticket.route);

            if (route) {
                textField.innerHTML += `<p>Номер билета: ${ticket.id}</p>`;
                textField.innerHTML += `<p>Маршрут: ${route.departurePoint} - ${route.arrivalPoint}. Дата и время отправления: ${route.departureDate} - ${route.departureTime}. Дата и время прибытия: ${route.arrivalDate} - ${route.arrivalTime}.</p>`;
                textField.innerHTML += `<p><button onclick="cancelBooking(${ticket.id})">Отменить бронь</p>`;
            }
        }
    }
    catch (error) {
        console.log("Error:", error);
    }
}

const notification = document.getElementById('modalMessage');
function cancelBooking(ticketId) {

    buttonFunction();
    notification.innerHTML = "";

    fetch(`http://localhost:8080/ticket/cancel?ticketId=${ticketId}`)
        .then(response => {
            return response.json();
        })
        .then(data => {

            if (data) {
                // уведомить об успешной отмене брони билета
                notification.innerHTML = "Бронь успешно отменена.";                

                var modal = document.getElementById('notification');
                modal.style.display = "block";

                var span = document.getElementsByClassName('close')[0];
                span.onclick = function () {
                    modal.style.display = "none";
                }
                window.onclick = function (event) {
                    if (event.target == modal) {
                        modal.style.display = "none";
                    }
                }
            }
            else {
                // уведомить об ошибке
                notification.innerHTML = "Произошла ошибка при попытке отменить бронь.";                

                var modal = document.getElementById('notification');
                modal.style.display = "block";

                var span = document.getElementsByClassName('close')[0];
                span.onclick = function () {
                    modal.style.display = "none";
                }
                window.onclick = function (event) {
                    if (event.target == modal) {
                        modal.style.display = "none";
                    }
                }
            }
        })
        .catch(error => {
            console.log("Error:", error);
        });
}