// области вывода
const notificationField = document.getElementById('notification');
const textField = document.getElementById('routes');
const buttonField = document.getElementById('additionalButton');

// кнопки
const specificButton = document.getElementById('searchSpecificButton');
const allButton = document.getElementById('searchAllButton');

// запрос /search/custom
specificButton.addEventListener('click', () => {

    notificationField.textContent = '';
    textField.textContent = '';
    buttonField.innerHTML = '';
    let isValid = true;

    // значения текстовых полей
    const departurePoint = document.getElementById('departurePoint').value;
    const arrivalPoint = document.getElementById('arrivalPoint').value;
    const departureDate = document.getElementById('departureDate').value;
    const departureTime = document.getElementById('departureTime').value;
    const transport = document.getElementById('transport').value;

    // проверить заполнение обязательных полей 
    if (!departurePoint || !arrivalPoint) {
        isValid = false;
        notificationField.innerHTML += '<p>Необходимо заполнить поля "ОТКУДА" и "КУДА"</p>';
    }

    // проверить соответствие паттернам
    if (departureDate) {
        if (!departureDate.match(document.getElementById('departureDate').pattern)) {
            isValid = false;
            notificationField.innerHTML += "<p>Указанная дата не соответствует формату ГГГГ-ММ-ДД (например, 2024-12-31)</p>";
        }
    }
    if (departureTime) {
        if (!departureTime.match(document.getElementById('departureTime').pattern)) {
            isValid = false;
            notificationField.innerHTML += "<p>Указанное время не соответствует формату ЧЧ:ММ (например, 09:00)</p>";
        }
    }

    if (isValid) {

        // HTTP-запрос
        fetch(`http://localhost:8080/search/custom?transport=${transport}&departurePoint=${departurePoint}&arrivalPoint=${arrivalPoint}&departureDate=${departureDate}&departureTime=${departureTime}`)
            .then(response => {
                return response.json();
            })
            .then(data => {

                let result = '';

                data.forEach(route => {
                    result += `<div class="result"><p>Маршрут: <b>${route.departurePoint} - ${route.arrivalPoint}</b></p><p>Дата и время отправления: <b>${route.departureDate} - ${route.departureTime}</b></p><p>Дата и время прибытия: <b>${route.arrivalDate} - ${route.arrivalTime}</b></p><p>Вид транспорта: <b>${route.transport}</b></p></div>`
                    result += `<div class="buttonContainer"><button type="button" onclick="showRouteDetails('${route.id}')">ПОДРОБНЕЕ</button></div>`
                });

                if (!result) {
                    result = 'Не удалось найти маршруты, соответствующие вашему запросу.';
                }

                textField.innerHTML = '<div class="header">РЕЗУЛЬТАТЫ ПОИСКА</div>';
                textField.innerHTML += result;
            })
            .catch(error => {
                console.error('Error:', error);
                textField.textContent = 'Произошла ошибка! Попробуйте позже.';
            });
    }
});

// запрос /search/global
allButton.addEventListener('click', () => {

    notificationField.textContent = '';
    textField.textContent = '';

    // HTTP-запрос 
    fetch('http://localhost:8080/search/global?date=')
        .then(response => {
            return response.json();
        })
        .then(data => {

            textField.innerHTML = '<div class="header"><b>РЕЗУЛЬТАТЫ ПОИСКА</b></div>';

            displayRoutes(data);
        })
        .catch(error => {
            console.log("Error:", error);
        });
});

// отобразить список маршрутов
function displayRoutes(data) {

    let newDate = '';
    let result = '';

    data.forEach(route => {
        result += `<div class="result"><p>Маршрут: <b>${route.departurePoint} - ${route.arrivalPoint}</b></p><p>Дата и время отправления: <b>${route.departureDate} - ${route.departureTime}</b></p><p>Дата и время прибытия: <b>${route.arrivalDate} - ${route.arrivalTime}</b></p><p>Вид транспорта: <b>${route.transport}</b></p></div>`
        result += `<div class="buttonContainer"><button type="button" onclick="showRouteDetails('${route.id}')">ПОДРОБНЕЕ</button></div>`
        newDate = route.departureDate;
    });

    if (result) {
        textField.innerHTML += `<p><h3>Дата отправления: ${newDate}<h3></p>`;
        textField.innerHTML += result;
        buttonField.innerHTML = `<div class="buttonContainer"><button type="button" onclick="getRoutes('${newDate}')">ПОКАЗАТЬ ЕЩЕ</button></div>`;
    }
    else {
        result += "<p>Маршрутов нет.</p>";
        buttonField.textContent = '';
    }
}

// получить новые маршруты
function getRoutes(date) {

    fetch(`http://localhost:8080/search/global?date=${date}`)
        .then(response => {
            return response.json();
        })
        .then(data => {
            displayRoutes(data);
        })
        .catch(error => {
            console.log("Error:", error);
        });
}


// перейти к бронированию билета
function showRouteDetails(routeId) {

    let places = 0;

    // HTTP-запрос
    fetch(`http://localhost:8080/search/getFreePlaces?routeId=${routeId}`)
        .then(response => {
            return response.json();
        })
        .then(data => {
            places = data;

            if (places > 0) {
                window.location.href = '/booking.html';
                localStorage.setItem('routeId', routeId);
            }
            else {
                // показать уведомление об отсутствии свободных мест на маршруте
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
            console.error('Error:', error);
        });
}