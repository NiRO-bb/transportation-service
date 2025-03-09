// Вернуться назад
const exitButton = document.getElementById('exitButton');
exitButton.addEventListener('click', () => {
    window.location.href = '/index.html';
});

// Информация о маршруте
const routeInfo = document.getElementById('routeInfo');
let routeId = localStorage.getItem('routeId');

fetch(`http://localhost:8080/search/route_info?routeId=${routeId}`)
.then(response => {
    return response.json();
})
.then(data => {
    let info = '';

    info += `<p>Маршрут: ${data.departurePoint} - ${data.arrivalPoint}</p>`;
    info += `<p>Дата и время отправления: ${data.departureDate} - ${data.departureTime}</p>`;
    info += `<p>Дата и время прибытия: ${data.arrivalDate} - ${data.arrivalTime}</p>`;
    info += `<p>Вид транспорта: ${data.transport}</p>`;

    // HTTP-запрос - количество свободных мест
    fetch(`http://localhost:8080/search/place_checking?routeId=${routeId}`)
    .then(response => {
        // обработка ответа
        return response.json();
    })
    .then(placeAmount => {
        info += `<p>Количество свободных мест: ${placeAmount}</p>`;
        routeInfo.innerHTML += info;
    });
})
.catch(error => {
    console.log("Error:", error);
});

// бронирование билета
const button = document.getElementById('bookingButton');
const notification = document.getElementById('notification');

button.addEventListener('click', () => {

    let isValid = true;

    const userLogin = document.getElementById('userLogin').value;
    const userPassword = document.getElementById('userPassword').value;

    // проверка заполненности полей
    if (!userLogin || !userPassword) {
        isValid = false;
        notification.textContent = 'Необходимо заполнить поля "Логин" и "Пароль"!';
    }

    if (isValid) {
        // данные в формате JSON
        const user = {
            login: userLogin,
            password: userPassword
        }

        // HTTP-запрос - авторизация
        fetch('http://localhost:8080/sign_in', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
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
                        // уведомление об успешной операции + ссылка на страницу для просмотра забронированных билетов
                        notification.innerHTML = 'Билет забронирован! Список забронированных билетов можно посмотреть <a href="/ticketList.html">здесь</a>.';

                        return response.text();
                    }  
                });
            }
            else {
                notification.textContent = 'Вы неверно указали логин и/или пароль!';
            }
        })
        .catch(error => {
            console.log("Error", error);
        });
    }
});