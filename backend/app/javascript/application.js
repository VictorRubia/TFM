// Entry point for the build script in your package.json
//= require_self
//= require_tree .
import "@hotwired/turbo-rails"
import "./controllers"
import * as bootstrap from "bootstrap"

var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'))
var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
    return new bootstrap.Popover(popoverTriggerEl)
})