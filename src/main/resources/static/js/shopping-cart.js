document.addEventListener("DOMContentLoaded", function() {
    const addButtonElements = document.querySelectorAll('.add');
    const addedServices = new Set(); // Conjunto para almacenar los nombres de los servicios agregados
    
    addButtonElements.forEach(function(addButton) {
        addButton.addEventListener('click', function(event) {
            const serviceBox = event.target.closest('.cajas'); // Encuentra el elemento .cajas más cercano al botón clicado
            const serviceName = serviceBox.querySelector('.service-type-text').textContent;
            const servicePrice = serviceBox.querySelector('.price').textContent;
            
            // Verificar si el servicio ya ha sido agregado
            if (addedServices.has(serviceName)) {
                // Si el servicio ya está en el carrito, no hacer nada
                return;
            }
            
            // Agregar el servicio al conjunto de servicios agregados
            addedServices.add(serviceName);
            
            // Crear elementos del carrito
            const cartItem = document.createElement('div');
            cartItem.classList.add('item');

            const itemIcon = document.createElement('div');
            itemIcon.classList.add('item-icon');
            // Aquí deberías configurar el icono de acuerdo a tu lógica
            // itemIcon.innerHTML = `<img src="ruta_al_icono">`;

            const itemDescription = document.createElement('div');
            itemDescription.classList.add('item-description');
            itemDescription.textContent = serviceName;

            const itemPrice = document.createElement('div');
            itemPrice.classList.add('item-price');
            itemPrice.textContent = servicePrice;

            const deleteButton = document.createElement('button');
            deleteButton.classList.add('item-delete');
            // Aquí deberías configurar el icono del botón de eliminar de acuerdo a tu lógica
            // deleteButton.innerHTML = `<img src="ruta_al_icono">`;
            
            deleteButton.addEventListener('click', function() {
                // Lógica para eliminar el artículo del carrito
                cartItem.remove();
                // Eliminar el servicio del conjunto de servicios agregados
                addedServices.delete(serviceName);
            });

            // Agregar elementos al DOM
            cartItem.appendChild(itemIcon);
            cartItem.appendChild(itemDescription);
            cartItem.appendChild(itemPrice);
            cartItem.appendChild(deleteButton);

            const servicesItems = document.querySelector('.services-items');
            servicesItems.appendChild(cartItem);
        });
    });
});
