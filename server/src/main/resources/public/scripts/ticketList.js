// Получить список билетов
const notificationField = document.getElementById('notification');
const textField = document.getElementById('tickets');
textField.textContent = '';

const button = document.getElementById('searchButton');
button.addEventListener('click', buttonFunction);

function buttonFunction() {
    notificationField.innerHTML = "";
    textField.innerHTML = "";

    const login = document.getElementById('userLogin');
    const password = document.getElementById('userPassword');

    let isValid = true;

    if (!login.value || !password.value) {
        isValid = false;
        notificationField.innerHTML = '<p>Необходимо заполнить все поля!</p>';
    }

    if (isValid) {

        // HTTP-запрос - авторизация
        fetch(`http://localhost:8080/sign_in?login=${login.value}&password=${password.value}`)
            .then(response => {
                return response.json();
            })
            .then(data => {
                if (!data) {
                    notificationField.innerHTML = '<p>Вы неверно указали логин и/или пароль!</p>';
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

// отобразить билеты пользователя
async function displayTickets() {
    textField.innerHTML = '<div class="header"><b>РЕЗУЛЬТАТ ПОИСКА</b></div>';

    try {
        // HTTP-запрос - список забронированных билетов пользователя
        const ticketsResponse = await fetch(`http://localhost:8080/ticket/getTickets?userLogin=${document.getElementById('userLogin').value}`);
        const ticketsData = await ticketsResponse.json();

        // HTTP-запрос - информация о маршруте по id
        const routesResponse = await fetch(`http://localhost:8080/search/getRouteByLogin?userLogin=${document.getElementById('userLogin').value}`)
        const routesData = await routesResponse.json();

        if (ticketsData.length === 0) {
            textField.innerHTML += '<p>У Вас нет забронированных билетов.</p>';
            return;
        }

        for (const ticket of ticketsData) {
            const route = routesData.find(route => route.id === ticket.route);

            if (route) {
                textField.innerHTML += `<div class="result"><p>Номер билета: <b>${ticket.id}</b></p><p>Маршрут: <b>${route.departurePoint} - ${route.arrivalPoint}</b></p><p>Дата и время отправления: <b>${route.departureDate} - ${route.departureTime}</b></p><p>Дата и время прибытия: <b>${route.arrivalDate} - ${route.arrivalTime}</b></p></div>`;
                textField.innerHTML += `<div class="buttonContainer"><p><button onclick="cancelBooking(${ticket.id})">ОТМЕНИТЬ БРОНЬ</p><div>`;
            }
        }
    }
    catch (error) {
        console.log("Error:", error);
    }
}

const notification = document.getElementById('modal-message');
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

                var modal = document.getElementById('modalWindow');
                modal.style.display = "flex";

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

                var modal = document.getElementById('modalWindow');
                modal.style.display = "flex";

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