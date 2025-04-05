// области вывода
const textField = document.getElementById('routes');
const buttonField = document.getElementById('additionalButton');

// кнопки
const specificButton = document.getElementById('searchSpecificButton');
const allButton = document.getElementById('searchAllButton');

// запрос /search/custom
specificButton.addEventListener('click', () => {

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
        textField.innerHTML += '<p>Необходимо заполнить поля "Откуда" и "Куда"!</p>';
    }

    // проверить соответствие паттернам
    if (departureDate) {
        if (!departureDate.match(document.getElementById('departureDate').pattern)) {
            isValid = false;
            textField.innerHTML += "<p>Указанная дата не соответствует формату! Например, 2024-12-31</p>";
        }
    }
    if (departureTime) {
        if (!departureTime.match(document.getElementById('departureTime').pattern)) {
            isValid = false;
            textField.innerHTML += "<p>Указанное время не соответствует формату! Например, 09:00</p>";
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
                    result += `<p>Маршрут: ${route.departurePoint} - ${route.arrivalPoint}. Дата и время отправления: ${route.departureDate} - ${route.departureTime}. Дата и время прибытия: ${route.arrivalDate} - ${route.arrivalTime}. Вид транспорта: ${route.transport}.</p>`
                    result += `<p><button type="button" onclick="showRouteDetails('${route.id}')">Подробнее</button></p>`
                });

                if (!result) {
                    result = 'Не удалось найти маршруты, соответствующие вашему запросу.';
                }

                textField.innerHTML = '<p><h2>Результаты поиска</h2></p>';
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

    textField.textContent = '';

    // HTTP-запрос 
    fetch('http://localhost:8080/search/global?date=')
        .then(response => {
            return response.json();
        })
        .then(data => {

            textField.innerHTML = '<p><h2>Результаты поиска</h2></p>';

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
        result += `<p>Маршрут: ${route.departurePoint} - ${route.arrivalPoint}. Дата и время отправления: ${route.departureDate} - ${route.departureTime}. Дата и время прибытия: ${route.arrivalDate} - ${route.arrivalTime}. Вид транспорта: ${route.transport}.</p>`
        result += `<p><button type="button" onclick="showRouteDetails('${route.id}')">Подробнее</button></p>`
        newDate = route.departureDate;
    });

    if (result) {
        textField.innerHTML += `<p><h3>Дата отправления: ${newDate}<h3></p>`;
        textField.innerHTML += result;
        buttonField.innerHTML = `<button type="button" onclick="getRoutes('${newDate}')">Показать еще</button>`;
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
                var modalWindow = document.getElementById('notification');
                modalWindow.style.display = "block";

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