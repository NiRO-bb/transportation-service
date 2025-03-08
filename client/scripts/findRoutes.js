// область вывода
const responseField = document.getElementById('response');

// кнопка
const btn = document.getElementById('searchButton');
btn.addEventListener('click', () => {
     
    responseField.textContent = '';
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
        responseField.innerHTML += '<p>Необходимо заполнить поля "Пункт отправления" и "Пункт прибытия"!</p>'; 
    }

    // проверить соответствие паттернам
    if (departureDate) {
        if (!departureDate.match(document.getElementById('departureDate').pattern)) {
            isValid = false;
            responseField.innerHTML += "<p>Указанная дата не соответствует формату! Например, 2024-12-31</p>";
        }
    }
    if (departureTime) {
        if (!departureTime.match(document.getElementById('departureTime').pattern)) {
            isValid = false;
            responseField.innerHTML += "<p>Указанное время не соответствует формату! Например, 12:00</p>";
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
            if (!response.ok) {
                if (response.status === 404) {
                    responseField.textContent = 'Не удалось найти маршруты, соответствующие вашему запросу.';
                }
            }
            else {
                return response.json();
            }
        })
        .then(data => {
            displayData(data);
        })
        .catch(error => {
            console.error('Error:', error);
            responseField.textContent = 'Ошибка при получении данных с сервера';
        });
    }
});

// вывести результаты запроса
function displayData(response) {
    let result = '';

    response.forEach(route => {
        result += `<p>Маршрут: ${route.departurePoint} - ${route.arrivalPoint}. Дата и время отправления: ${route.departureDate} - ${route.departureTime}. Дата и время прибытия: ${route.arrivalDate} - ${route.arrivalTime}. Вид транспорта: ${route.transport}.</p>`
        result += `<p><button type="button" onclick="showRouteDetails('${route.id}')">Подробнее</button></p>`
    });

    if (!result) {
        result += 'Не удалось найти маршруты, соответствующие вашему запросу.';
    }

    responseField.innerHTML = result;
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
            window.location.href = 'booking.html';
            localStorage.setItem('routeId', routeId);
        } 
        else {
            // показать уведомление об отсутствии свободных мест на маршруте
            var modal = document.getElementById('notification');
            modal.style.display = "block";

            var span = document.getElementsByClassName('close')[0];
            span.onclick = function() {
                modal.style.display = "none";
            }
            window.onclick = function(event) {
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