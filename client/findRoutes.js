// кнопка
const btn = document.getElementById('searchButton');

btn.addEventListener('click', () => {
    // значения текстовых полей
    const departurePoint = document.getElementById('departurePoint').value;
    const arrivalPoint = document.getElementById('arrivalPoint').value;
    const departureDate = document.getElementById('departureDate').value;
    const departureTime = document.getElementById('departureTime').value;
    const transport = document.getElementById('transport').value;

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
    .then(response => {
        displayData(response);
    })
    .catch(error => {
        console.error('Error:', error);
        responseField.textContent = 'Ошибка при получении данных с сервера';
    });
});

// вывести результаты запроса
function displayData(response) {
    // область для вывода результата запроса
    const responseField = document.getElementById('response');
    let result = '';

    response.forEach(route => {
        result += `<p>Маршрут: ${route.departurePoint} - ${route.arrivalPoint}. Дата и время отправления: ${route.departureDate} - ${route.departureTime}. Дата и время прибытия: ${route.arrivalDate} - ${route.arrivalTime}. Вид транспорта: ${route.transport}.</p>`
    });

    responseField.innerHTML += result;
}