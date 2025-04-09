// информация о маршруте
const routeInfo = document.getElementById('routeInfo');
let routeId = localStorage.getItem('routeId');

getRouteInfo();

function getRouteInfo() {
    routeInfo.innerHTML = '';
    fetch(`http://localhost:8080/search/getRoute?routeId=${routeId}`)
        .then(response => {
            return response.json();
        })
        .then(data => {
            let info = '';

            info = `<div class="result"><p>Маршрут: <b>${data.departurePoint} - ${data.arrivalPoint}</b></p><p>Дата и время отправления: <b>${data.departureDate} - ${data.departureTime}</b></p><p>Дата и время прибытия: <b>${data.arrivalDate} - ${data.arrivalTime}</b></p><p>Вид транспорта: <b>${data.transport}</b></p></div>`;

            // HTTP-запрос
            fetch(`http://localhost:8080/search/getFreePlaces?routeId=${routeId}`)
                .then(response => {
                    return response.json();
                })
                .then(data => {
                    info += `<div class="result"><p>Количество свободных мест: <b>${data}</b></p></div>`;
                    routeInfo.innerHTML += info;
                });
        })
        .catch(error => {
            console.log("Error:", error);
        });
}

// бронирование билета
const button = document.getElementById('bookingButton');
const notificationField = document.getElementById('notification');

button.addEventListener('click', () => {

    let isValid = true;

    const userLogin = document.getElementById('userLogin').value;
    const userPassword = document.getElementById('userPassword').value;

    // проверка заполненности полей
    if (!userLogin || !userPassword) {
        isValid = false;
        notificationField.innerHTML = '<p>Необходимо заполнить поля "ЛОГИН" и "ПАРОЛЬ"!<p>';
    }

    if (isValid) {

        // HTTP-запрос - авторизация
        fetch(`http://localhost:8080/sign_in?login=${userLogin}&password=${userPassword}`)
            .then(response => {
                return response.json();
            })
            .then(data => {
                if (data) {

                    // данные в формате JSON
                    const ticket = {
                        id: 0,
                        userLogin: userLogin,
                        route: localStorage.getItem('routeId')
                    }

                    // HTTP-запрос - бронирование билета
                    fetch('http://localhost:8080/ticket/book', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(ticket)
                    })
                        .then(response => {
                            if (response.ok) {
                                return response.text();
                            }
                        })
                        .then(data => {
                            if (data) {
                                // уведомление об успешной операции + ссылка на страницу для просмотра забронированных билетов
                                notificationField.innerHTML = '<p>Билет забронирован! Список забронированных билетов можно посмотреть в разделе <a href="ticketList.html">МОИ БИЛЕТЫ</a>.</p>';
                                getRouteInfo();
                            }
                            else {
                                notificationField.innerHTML = '<p>Произошла ошибка! Попробуйте позже.</p>';
                            }
                        });
                }
                else {
                    notificationField.innerHTML = '<p>Вы неверно указали логин и/или пароль!</p>';
                }
            })
            .catch(error => {
                console.log("Error", error);
            });
    }
});