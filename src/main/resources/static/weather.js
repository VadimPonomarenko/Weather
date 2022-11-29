const baseUrl = "http://192.168.1.74:8080"
// const data_url = "http://192.168.1.74:8080/getData"
const expectations_url = baseUrl + "/getTodayExpectations"
const secondDay_url = baseUrl + "/getSecondDay"
const thirdDay_url = baseUrl + "/getThirdDay"
const fourthDay_url = baseUrl + "/getFourthDay"
const fifthDay_url = baseUrl + "/getFifthDay"

let is_loading = true
const preload = document.getElementById("preload")
const content = document.getElementById("content")
const city = document.getElementById("city")
const currentTemp = document.getElementById("currentTemp")
const weekDay = document.getElementById("currentWeekDay")
const todayDate = document.getElementById("todayDate")
const futureTemp1 = document.getElementById("futureTemp1")
const futureTemp2 = document.getElementById("futureTemp2")
const currentTime = document.getElementById("currentTime")

const img2day = document.getElementById("img2day")
const img3day = document.getElementById("img3day")
const img4day = document.getElementById("img4day")
const img5day = document.getElementById("img5day")

function fillTable () {
        fetch(expectations_url)
            .then(res => res.json())
            .then( (s) => {
                    if (is_loading) {
                        content.classList.add("active")
                        preload.classList.remove("active")
                        is_loading = false
                    }
                    city.innerHTML = `<b>${s.city}</b>`
                    currentTemp.innerHTML = `<b>${s.currentTemp}°C</b>`
                    todayDate.innerHTML = `<b>${s.dayOfMonth}</b>`
                    weekDay.innerHTML = `<b>${s.weekDay}</b>`
                    futureTemp1.innerHTML = `<b>${s.expectations1}</b>`
                    futureTemp2.innerHTML = `<b>${s.expectations2}</b>`
                    document.getElementById("secondDay").innerHTML = `${s.secondDay}`
                    document.getElementById("thirdDay").innerHTML = `${s.thirdDay}`
                    document.getElementById("fourthDay").innerHTML = `${s.fourthDay}`
                    document.getElementById("fifthDay").innerHTML = `${s.fifthDay}`
                    document.getElementById("img1day").src = s.todayIcon
                    document.getElementById("fog1dayText").innerHTML = s.fog
                        if (s.fog == 0) {
                            document.querySelector("#fog1day").style.display = "none"
                            document.querySelector("#fog1dayText").style.display = "none"
                        }
                    let vid = document.getElementById("myVideo").src = s.todayVideo
                vid.load()
                vid.play()
            })
        fetch(secondDay_url)
            .then(res => res.json())
            .then( (s) => {
                    document.getElementById("day2highest").innerHTML = `${s.highestTemp}°C`
                    document.getElementById("day2lowest").innerHTML = `${s.lowestTemp}°C`
                    document.getElementById("day2info").innerHTML = `${s.explanation}`
                    img2day.src = s.img
                    document.getElementById("fog2dayText").innerHTML = s.fog
                    if (s.fog == 0) {
                        document.querySelector("#fog2day").style.display = "none"
                        document.querySelector("#fog2dayText").style.display = "none"
                    }
            })
        fetch(thirdDay_url)
            .then(res => res.json())
            .then( (s) => {
                    document.getElementById("day3highest").innerHTML = `${s.highestTemp}°C`
                    document.getElementById("day3lowest").innerHTML = `${s.lowestTemp}°C`
                    document.getElementById("day3info").innerHTML = `${s.explanation}`
                    img3day.src = s.img
                    document.getElementById("fog3dayText").innerHTML = s.fog
                    if (s.fog == 0) {
                        document.querySelector("#fog3day").style.display = "none"
                        document.querySelector("#fog3dayText").style.display = "none"
                    }
            })
        fetch(fourthDay_url)
            .then(res => res.json())
            .then( (s) => {
                    document.getElementById("day4highest").innerHTML = `${s.highestTemp}°C`
                    document.getElementById("day4lowest").innerHTML = `${s.lowestTemp}°C`
                    document.getElementById("day4info").innerHTML = `${s.explanation}`
                    img4day.src = s.img
                    document.getElementById("fog4dayText").innerHTML = s.fog
                    if (s.fog == 0) {
                        document.querySelector("#fog4day").style.display = "none"
                        document.querySelector("#fog4dayText").style.display = "none"
                    }
            })
        fetch(fifthDay_url)
            .then(res => res.json())
            .then( (s) => {
                    document.getElementById("day5highest").innerHTML = `${s.highestTemp}°C`
                    document.getElementById("day5lowest").innerHTML = `${s.lowestTemp}°C`
                    document.getElementById("day5info").innerHTML = `${s.explanation}`
                    img5day.src = s.img
                    document.getElementById("fog5dayText").innerHTML = s.fog
                    if (s.fog == 0) {
                        document.querySelector("#fog5day").style.display = "none"
                        document.querySelector("#fog5dayText").style.display = "none"
                    }
            })
}

let now;
Date.prototype.timeNow = function () {
    return ((this.getHours() < 10)?"0":"") + this.getHours() +":"+ ((this.getMinutes() < 10)?"0":"") + this.getMinutes();
}
function updateTime() {
    now = new Date();
    currentTime.innerHTML = '' + now.timeNow();
}

setInterval(updateTime, 30000) // 30 sec
// setInterval(fillTable, 600000)  // 10 min
setInterval(fillTable, 60000)  // 1 min
updateTime()
fillTable()
