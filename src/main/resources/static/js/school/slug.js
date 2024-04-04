function convertToSlug(e) {
    var input = e.value;
    var slug = slugify(input);
    document.getElementById("slug").value = slug;
}

function slugify(text) {
    if(text.length == 0){
        return ''
    }
    var slug = text.toLowerCase()
        .replace(/đ/g, 'd')             // Chuyển chữ "đ" thành "d"
        //.replace(/ /g, '-')             // Chuyển dấu cách thành dấu gạch ngang
        .normalize('NFD')                // Chuẩn hóa Unicode (chuyển đổi ký tự có dấu thành không dấu)
        .replace(/[\u0300-\u036f]/g, '') // Loại bỏ ký tự có dấu
        .replace(/[^\w\-]+/g, '')        // Loại bỏ ký tự không phải chữ cái, số, dấu gạch ngang
        .replace(/\-\-+/g, '-')          // Loại bỏ các dấu gạch ngang liên tiếp
        .replace(/^-+/, '')              // Loại bỏ dấu gạch ngang ở đầu
        .replace(/-+$/, '');             // Loại bỏ dấu gạch ngang ở cuối
    return slug + '.eduhub@hunre.edu.vn';
}