function showOptionals(year) {
    if (year === 2) {
        document.getElementById("optionalInfo2").style.display = "block";
        document.getElementById("optionalInfo3").style.display = "none";
        document.getElementById("regulament").style.display = "none";
    }
    else {
        document.getElementById("optionalInfo2").style.display = "none";
        document.getElementById("optionalInfo3").style.display = "block";
        document.getElementById("regulament").style.display = "none";
    }
}

function adminPage() {
    document.getElementById("modificaOpt").style.display = "block";
    document.getElementById("vizStuds").style.display = "block";
    document.getElementById("modifDistrib").style.display = "block";
}