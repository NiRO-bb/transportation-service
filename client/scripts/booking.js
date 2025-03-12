// вернуться назад
const exitButton = document.getElementById('exitButton');
exitButton.addEventListener('click', () => {
    window.location.href = '/index.html';
});

// информация о маршруте
const routeInfo = document.getElementById('routeInfo');
let routeId = localStorage.getItem('routeId');

fetch(`http://localhost:8080/search/getRoute?routeId=${routeId}`)
    .then(response => {
        return response.json();
    })
    .then(data => {
        let info = '';

        info += `<p>Маршрут: ${data.departurePoint} - ${data.arrivalPoint}</p>`;
        info += `<p>Дата и время отправления: ${data.departureDate} - ${data.departureTime}</p>`;
        info += `<p>Дата и время прибытия: ${data.arrivalDate} - ${data.arrivalTime}</p>`;
        info += `<p>Вид транспорта: ${data.transport}</p>`;

        // HTTP-запрос
        fetch(`http://localhost:8080/search/getFreePlaces?routeId=${routeId}`)
            .then(response => {
                return response.json();
            })
            .then(data => {
                info += `<p>Количество свободных мест: ${data}</p>`;
                routeInfo.innerHTML += info;
            });
    })
    .catch(error => {
        console.log("Error:", error);
    });

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
        notificationField.textContent = 'Необходимо заполнить поля "Логин" и "Пароль"!';
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
                                notificationField.innerHTML = 'Билет забронирован! Список забронированных билетов можно посмотреть <a href="/ticketList.html">здесь</a>.';
                            }
                            else {
                                notificationField.innerHTML = 'Произошла ошибка! Попробуйте позже.';
                            }
                        });
                }
                else {
                    notificationField.textContent = 'Вы неверно указали логин и/или пароль!';
                }
            })
            .catch(error => {
                console.log("Error", error);
            });
    }
});