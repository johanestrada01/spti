// agregar imágenes a los botones
function addImage(brandName) {
    // busca las imagenes correspondientes a la marca
    let images = obtenerImagenesDeMarca(brandName); // array de objetos con src y alt

    // encontrar el boton correspondiente a la marca
    let buttons = document.querySelectorAll('.rectangle-grid button');
    let button;
    buttons.forEach(function(btn) {
        if (btn.innerText.trim() === brandName) {
            button = btn;
        }
    });
    images.forEach(function(image) {// crear y agrega las imágenes al botón
        let img = createImage(image.src, image.alt);
        button.appendChild(img);
    });
    return false; // evitar que el formulario se envíe
}

// Crea una imagen
function createImage(src, alt) {
    let img = document.createElement('img');
    img.src = src;
    img.alt = alt;
    return img;
}


function obtenerImagenesDeMarca(brandNames) {
    let images = [];
    brandNames.forEach(function(brandName) {
        let imagePath = "../static/images/brands/" + brandName.toLowerCase() + ".png";
        images.push({ src: imagePath, alt: brandName });
    });
    return images;
}
