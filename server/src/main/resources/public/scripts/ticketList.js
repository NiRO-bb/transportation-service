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

    // HTTP-запрос - авторизация
    fetch(`http://localhost:8080/sign_in?login=${login.value}&password=${password.value}`)
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
            displayTickets();
        })
        .catch(error => {
            notificationField.innerHTML = '';
            error.message.forEach(msg => {
                console.log("Error:", msg);
                notificationField.innerHTML += `<p>${msg}</p>`;
            });
        });
}

// отобразить билеты пользователя
function displayTickets() {
    textField.innerHTML = '<div class="header"><b>РЕЗУЛЬТАТ ПОИСКА</b></div>';

    try {
        // HTTP-запрос - список забронированных билетов пользователя
        fetch(`http://localhost:8080/ticket/getTickets?userLogin=${document.getElementById('userLogin').value}`)
            .then(response => {
                if (!response.ok) {
                    return response.json()
                        .then(error => {
                            throw {
                                message: error.message,
                                status: error.status
                            }
                        });
                }
                return response.json();
            })
            .then(ticketsData => {
                // HTTP-запрос - информация о маршруте по id
                fetch(`http://localhost:8080/search/getRouteByLogin?userLogin=${document.getElementById('userLogin').value}`)
                    .then(response => {
                        if (!response.ok) {
                            return response.json()
                                .then(error => {
                                    throw {
                                        message: error.message,
                                        status: error.status
                                    }
                                });
                        }
                        return response.json();
                    })
                    .then(routesData => {
                        if (ticketsData.length === 0) {
                            textField.innerHTML += '<p>У Вас нет забронированных билетов.</p>';
                            return;
                        }

                        for (let ticket of ticketsData) {
                            let route = routesData.find(route => route.id === ticket.route);

                            if (route) {
                                textField.innerHTML += `<div class="result"><p>Номер билета: <b>${ticket.id}</b></p><p>Маршрут: <b>${route.departurePoint} - ${route.arrivalPoint}</b></p><p>Дата и время отправления: <b>${route.departureDate} - ${route.departureTime}</b></p><p>Дата и время прибытия: <b>${route.arrivalDate} - ${route.arrivalTime}</b></p></div>`;
                                textField.innerHTML += `<div class="buttonContainer"><p><button onclick="cancelBooking(${ticket.id})">ОТМЕНИТЬ БРОНЬ</p><div>`;
                            }
                        }
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
    catch (error) {
        console.log("Error:", error);
    }
}

const notification = document.getElementById('modal-message');
function cancelBooking(ticketId) {

    buttonFunction();
    notification.innerHTML = "";

    // HTTP-запрос - отмена бронирования
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