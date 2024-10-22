// agregar imágenes a los botones
function addImage(brandName) {
    // busca las imagenes correspondientes a la marca
    var images = obtenerImagenesDeMarca(brandName); // array de objetos con src y alt

    // encontrar el boton correspondiente a la marca
    var buttons = document.querySelectorAll('.rectangle-grid button');
    var button;
    buttons.forEach(function(btn) {
        if (btn.innerText.trim() === brandName) {
            button = btn;
        }
    });
    images.forEach(function(image) {// crear y agrega las imágenes al botón
        var img = createImage(image.src, image.alt);
        button.appendChild(img);
    });
    return false; // evitar que el formulario se envíe
}

// Crea una imagen
function createImage(src, alt) {
    var img = document.createElement('img');
    img.src = src;
    img.alt = alt;
    return img;
}


function obtenerImagenesDeMarca(brandNames) {
    var images = [];
    brandNames.forEach(function(brandName) {
        var imagePath = "../static/images/brands/" + brandName.toLowerCase() + ".png";
        images.push({ src: imagePath, alt: brandName });
    });
    return images;
}
