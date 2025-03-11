// область вывода
const textField = document.getElementById('response');
const buttonField = document.getElementById('additionalButton');

// кнопки
const specificButton = document.getElementById('searchSpecificButton');
const allButton = document.getElementById('searchAllButton'); 

specificButton.addEventListener('click', () => {

    textField.textContent = '';
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
        textField.innerHTML += '<p>Необходимо заполнить поля "Пункт отправления" и "Пункт прибытия"!</p>';
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
            textField.innerHTML += "<p>Указанное время не соответствует формату! Например, 12:00</p>";
        }
    }

    if (isValid) {
        // данные в формате JSON
        const data = {
            id: 0,
            transport: transport,
            places: 0,
            departurePoint: departurePoint,
            arrivalPoint: arrivalPoint,
            departureDate: departureDate,
            departureTime: departureTime,
            arrivalDate: "",
            arrivalTime: ""
        };

        // HTTP-запрос
        fetch('http://localhost:8080/search/global', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                // обработка ответа
                return response.json();
            })
            .then(data => {
                if (data.length > 0) {
                    displayData(data);
                }
                else {
                    textField.innerHTML = '<p><h2>Результаты поиска</h2></p><p>Не удалось найти маршруты, соответствующие вашему запросу.</p>';
                }
            })
            .catch(error => {
                console.error('Error:', error);
                textField.textContent = 'Ошибка при получении данных с сервера';
            });
    }
});

allButton.addEventListener('click', () => {

    textField.textContent = '';

    fetch('http://localhost:8080/search/all?date=')
    .then(response => {
        return response.json();
    })
    .then (data => {
        textField.innerHTML = '<p><h2>Результаты поиска</h2></p>';

        let result = '';
        let date = '';

        data.forEach(route => {
            result += `<p>Маршрут: ${route.departurePoint} - ${route.arrivalPoint}. Дата и время отправления: ${route.departureDate} - ${route.departureTime}. Дата и время прибытия: ${route.arrivalDate} - ${route.arrivalTime}. Вид транспорта: ${route.transport}.</p>`
            date = route.departureDate;
        });

        textField.innerHTML += `<p><h3>Дата отправления: ${date}<h3></p>`;

        if (!result) {
            result += "<p>Маршрутов нет.</p>";
        }
        else {
            buttonField.innerHTML = `<button type="button" onclick="getNewRoutes('${date}')">Показать еще</button>`;
        }
        textField.innerHTML += result;
    })
    .catch(error => {
        console.log("Error:", error);
    });
});

// получить новые маршруты (для поиска без указания параметров)
function getNewRoutes(date) {

    fetch(`http://localhost:8080/search/all?date=${date}`)
    .then(response => {
        return response.json();
    })
    .then (data => {
        let newDate = '';
        let result = '';

        data.forEach(route => {
            result += `<p>Маршрут: ${route.departurePoint} - ${route.arrivalPoint}. Дата и время отправления: ${route.departureDate} - ${route.departureTime}. Дата и время прибытия: ${route.arrivalDate} - ${route.arrivalTime}. Вид транспорта: ${route.transport}.</p>`
            newDate = route.departureDate;
        });
        
        if (result) {
            textField.innerHTML += `<p><h3>Дата отправления: ${newDate}<h3></p>`;     
            textField.innerHTML += result;
            buttonField.innerHTML = `<button type="button" onclick="getNewRoutes('${newDate}')">Показать еще</button>`;
        }
        else {
            buttonField.textContent = '';
        }
    })
    .catch(error => {
        console.log("Error:", error);
    });
}

// вывести результаты запроса
function displayData(response) {
    let result = '<p><h2>Результаты поиска</h2></p>';

    response.forEach(route => {
        result += `<p>Маршрут: ${route.departurePoint} - ${route.arrivalPoint}. Дата и время отправления: ${route.departureDate} - ${route.departureTime}. Дата и время прибытия: ${route.arrivalDate} - ${route.arrivalTime}. Вид транспорта: ${route.transport}.</p>`
        result += `<p><button type="button" onclick="showRouteDetails('${route.id}')">Подробнее</button></p>`
    });

    if (!result) {
        result += 'Не удалось найти маршруты, соответствующие вашему запросу.';
    }

    textField.innerHTML = result;
}

function showRouteDetails(routeId) {

    let placeAmount = 0;

    // HTTP-запрос
    fetch(`http://localhost:8080/search/place_checking?routeId=${routeId}`)
        .then(response => {
            // обработка ответа
            return response.json();
        })
        .then(response => {
            placeAmount = response;

            if (placeAmount > 0) {
                window.location.href = '/booking.html';
                localStorage.setItem('routeId', routeId);
            }
            else {
                // показать уведомление об отсутствии свободных мест на маршруте
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
            console.error('Error:', error);
        });
}