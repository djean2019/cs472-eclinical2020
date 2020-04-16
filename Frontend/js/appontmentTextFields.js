(function getPatients() {
    fetch("http://localhost:8080/miuclinical/api/specializationcontroller")
        .then(response => response.json())
        .then((items) => {
            console.log(items)
            patients = items.data;
            items.data.forEach((item, index) => {
                const row = buildRow(item, index);
                console.log(item);
               // $(table).find('tbody').append(row);
            })
        })
        .catch(error => alert(error));
})();