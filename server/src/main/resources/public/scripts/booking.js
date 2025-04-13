// информация о маршруте
const routeInfo = document.getElementById('routeInfo');
let routeId = localStorage.getItem('routeId');

// бронирование билета
const button = document.getElementById('bookingButton');
const notificationField = document.getElementById('notification');

getRouteInfo();

function getRouteInfo() {
    routeInfo.innerHTML = '';
    // HTTP-запрос - информация о маршруте
    fetch(`http://localhost:8080/search/getRoute?routeId=${routeId}`)
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
        .then(data => {
            let info = '';
            info = `<div class="result"><p>Маршрут: <b>${data.departurePoint} - ${data.arrivalPoint}</b></p><p>Дата и время отправления: <b>${data.departureDate} - ${data.departureTime}</b></p><p>Дата и время прибытия: <b>${data.arrivalDate} - ${data.arrivalTime}</b></p><p>Вид транспорта: <b>${data.transport}</b></p></div>`;

            // HTTP-запрос - количество свобоодных мест на маршруте
            fetch(`http://localhost:8080/search/getFreePlaces?routeId=${routeId}`)
                .then(response => {
                    if (!response.ok) {
                        return response.json()
                            .then(error => {
                                throw new Error(error.message);
                            });
                    }
                    return response.json();
                })
                .then(data => {
                    info += `<div class="result"><p>Количество свободных мест: <b>${data}</b></p></div>`;
                    routeInfo.innerHTML += info;
                });
        })
        .catch(error => {
            notificationField.innerHTML = '';
            error.message.forEach(msg => {
                console.log("Error:", msg);
                notificationField.innerHTML += `<p>${msg}</p>`;
            });
        });
}

button.addEventListener('click', () => {

    const userLogin = document.getElementById('userLogin').value;
    const userPassword = document.getElementById('userPassword').value;

    // HTTP-запрос - авторизация
    fetch(`http://localhost:8080/sign_in?login=${userLogin}&password=${userPassword}`)
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
                    if (!response.ok) {
                        return response.json()
                            .then(error => {
                                throw new Error(error.message);
                            });
                    }
                    return response.json();
                })
                .then(() => {
                    // уведомление об успешной операции + ссылка на страницу для просмотра забронированных билетов
                    notificationField.innerHTML = '<p>Билет забронирован! Список забронированных билетов можно посмотреть в разделе <a href="ticketList.html">МОИ БИЛЕТЫ</a>.</p>';
                    getRouteInfo();
                });
        })
        .catch(error => {
            notificationField.innerHTML = '';
            error.message.forEach(msg => {
                console.log("Error", msg);
                notificationField.innerHTML += `<p>${msg}</p>`;
            });
        });
});