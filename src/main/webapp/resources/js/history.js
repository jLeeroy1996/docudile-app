/**
 * Created by PaulRyan on 2/15/2016.
 */
var container = document.querySelector('.dd-navtree');

container.addEventListener('click', function(e) {
    if (e.target != e.currentTarget) {
        e.preventDefault();
        // e.target is the image inside the link we just clicked.

        var data = e.target.getAttribute('data-name'),
            url = "/home" + data;
        history.pushState(data, null, url);
    }
    e.stopPropagation();
}, false);
