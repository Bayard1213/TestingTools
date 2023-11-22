document.getElementById('dataForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Предотвращаем стандартное поведение формы
  
    //вначале убираем класс "duplicate" у всех элементов input
    this.querySelectorAll('input[name="key"]').forEach(input => {
      input.classList.remove('is-invalid');
    });
     //собираем все значения input
    const inputs = Array.from(this.querySelectorAll('input[name="key"]'));
    const keys = inputs.map(input => input.value.trim());
     
    //используем объект для отслеживания количества каждого значения
    const valueCount = keys.reduce((obj, key) => {
      obj[key] = (obj[key] || 0) + 1;
      return obj;
     }, {});
  
    let hasDuplicates = false;
    inputs.forEach(input => {
      if (valueCount[input.value.trim()] > 1) {
        input.classList.add('is-invalid');
        hasDuplicates = true;
      }
     });
     
     //если есть дубликаты, предотвращаем отправку формы
    if (hasDuplicates) {
      alert('Ключи не должны совпадать!');
      event.preventDefault();
      return;
    }
    
    const jsonData = {}; //объект для сбора данных
  
    const fieldGroups = document.querySelectorAll('#fieldsContainer .fieldGroup');
    fieldGroups.forEach(group => {
      const keyInput = group.querySelector('input[name="key"]'); // Элемент input с именем ключа
      const templateFieldInput = group.querySelector('.templateFields input, .templateFields select'); // Элемент input или select, добавленный из шаблона
  
      if (keyInput && keyInput.value && templateFieldInput) {
        const key = keyInput.value;
        const value = templateFieldInput.value;
        const name = templateFieldInput.name;
  
        //создаем вложенный объект, если он не существует
        if (!jsonData[key]) jsonData[key] = {};
        if (templateFieldInput.name == 'inputMin' || templateFieldInput.name == 'inputMax'){
          const inputMin = document.getElementById('inputMin').value;
          const inputMax = document.getElementById('inputMax').value;
          jsonData[key] = {"integer" : inputMin + ":" + inputMax};
          console.log(jsonData);
          return;
        }
        //добавляем значение из поля вложенного объекта
        jsonData[key][name] = value;
      }
      console.log(jsonData);
    });
    //преобразуем объект в JSON-строку
    const jsonString = JSON.stringify(jsonData);
    //отправляем JSON на сервер
    fetch('/json', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: jsonString
    })
    .then(response => {
      if (!response.ok){
        throw new Error('Network response was not ok');
      } 
      return response.json();
    })
    .then(data => {
      //обработка ответа сервера
      console.log('Server response:', data)
      downloadJson(data);
    })
  .catch((error) => {
      //обрабатываем возможные ошибки сети
      console.error('There has been a problem with your fetch operation:', error);
    });
  });
  
  function downloadJson(jsonData) {
    const jsonString = JSON.stringify(jsonData);
    const blob = new Blob([jsonString], {type: 'application/json'});
    const url = URL.createObjectURL(blob);
    const downloadElement = document.createElement('a');
    downloadElement.href = url;
    downloadElement.download = 'data.json';
    document.body.appendChild(downloadElement); //добавление в DOM
    downloadElement.click();
    document.body.removeChild(downloadElement); //удаление из DOM 
    URL.revokeObjectURL(url);
  }