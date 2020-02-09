const DAYS = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];

/**
 * Draw time panel
 */
function drawTimePanel(dateRange) {
    let table = document.getElementById("timetable");
    let head = table.insertRow(0);
    head.insertCell(0).innerHTML = '';

    for (let i = 1; i <= DAYS.length; i++) {
        let dayCell = head.insertCell(i);
        let container = document.createElement("div");

        container.style.display = "grid";

        let dayName = DAYS[i - 1];
        let dayNameSpan = document.createElement("span");
        dayNameSpan.innerText = dayName;

        container.appendChild(dayNameSpan);
        dayCell.appendChild(container);
    }

    let hour = 0;
    let min = 0;
    const MAXIMUM_DAYS = 7;
    const MAXIMUM_HOURS = 23;

    let fromDate = new Date(dateRange.fromDate);

    for (let i = 0; i <= MAXIMUM_HOURS; i++) {
        let row = table.insertRow(i + 1);

        const tomorrow = new Date(fromDate);

        for (let j = 0; j <= MAXIMUM_DAYS; j++) {
            let cell = row.insertCell(j);
            cell.width = 100;

            let strHour = hour < 10 ? '0' + hour : hour;
            let strMin = min < 10 ? '0' + min : min;
            if (j === 0) {
                cell.innerText = strHour + ":" + strMin;
            } else {
                cell.setAttribute("class", "eventCell");

            }

            if (j !== 0) {
                let dayInfo = tomorrow.getDate() + "." + (tomorrow.getMonth() + 1) + "." + tomorrow.getFullYear();
                cell.setAttribute("id", dayInfo + "-" + strHour.toString() + ":" + strMin.toString());
                tomorrow.setDate(tomorrow.getDate() + 1);
            }
        }
        hour++;
    }
}

/**
 * Add title to element by event
 *
 * @param event event info
 * @param element to add attribute
 */
function addTitle(event, element) {
    let userFullName = event.user.firstName + " " + event.user.lastName;
    if (event.title != null) {
        element.setAttribute("title", userFullName + " : " + event.title);
    } else {
        element.setAttribute("title", userFullName);
    }
}

/**
 * Create link for event
 *
 * @param rootUrl root url to append
 * @param eventBackgroundColor element background color
 * @param eventForegroundColor element foreground color
 * @param event event
 * @param fromHours event start hour
 * @param fromMinutes event start minutes
 * @param toHours event end hour
 * @param toMinutes event end minutes
 * @param elementId element id to add created link
 */
function createLink(rootUrl, eventBackgroundColor, eventForegroundColor, event, fromHours, fromMinutes, toHours, toMinutes, elementId) {
    let element = document.createElement("a");
    element.setAttribute("style",
        "background-color:" + eventBackgroundColor + "; " +
        "border-color: " + shadeColor(eventBackgroundColor, 100) + "; " +
        "color: " + eventForegroundColor + "; " +
        "margin: 2px;");
    element.setAttribute("data-toggle", "");
    element.setAttribute("data-placement", "top");
    element.setAttribute("class", "btn btn-primary");
    addTitle(event, element);
    element.setAttribute("href", rootUrl + event.id);
    element.innerText = fromHours + ":" + fromMinutes + " - " + toHours + ":" + toMinutes;

    let brElement = document.createElement("br");

    let elementById = document.getElementById(elementId);
    if (elementById !== null) {
        elementById.appendChild(element);
        elementById.appendChild(brElement);
    } else {
        console.log("Can not print child for event: ", event, "elementId: ", elementId)
    }
}


/**
 * Shades color on given %
 *
 * @param color color
 * @param percent percent to shade
 * @returns {string} color in HEX
 */
function shadeColor(color, percent) {

    let R = parseInt(color.substring(1, 3), 16);
    let G = parseInt(color.substring(3, 5), 16);
    let B = parseInt(color.substring(5, 7), 16);

    R = parseInt(R * (100 + percent) / 100);
    G = parseInt(G * (100 + percent) / 100);
    B = parseInt(B * (100 + percent) / 100);

    R = (R < 255) ? R : 255;
    G = (G < 255) ? G : 255;
    B = (B < 255) ? B : 255;

    let RR = ((R.toString(16).length === 1) ? "0" + R.toString(16) : R.toString(16));
    let GG = ((G.toString(16).length === 1) ? "0" + G.toString(16) : G.toString(16));
    let BB = ((B.toString(16).length === 1) ? "0" + B.toString(16) : B.toString(16));

    return "#" + RR + GG + BB;
}
/**
 * Pseudo - random color generation by uuid
 * @param event to generate code from its id
 * @returns {string} color in hex
 */
function generateEventColorForEvent(event) {
    if (event != null) {
        let subCore = event.id.substr(9, 12);
        let core = event.id.substr(0, 3);
        return subCore[0] + core[0] + subCore[1] + core[1] + subCore[3] + core[2];
    }
    return Math.random().toString(16).slice(2, 8);
}

/**
 * Fill days by range
 *
 * @param range of dates
 */
function fillDays(range) {
    let from = new Date(range.fromDate);
    for (let i = 1; i <= 7; i++) {
        const tomorrow = new Date(from);
        tomorrow.setDate(tomorrow.getDate() + (i - 1));

        let table = document.getElementById("timetable");
        let cell = table.rows[0].cells[i];


        let dateSpan = document.createElement("span");
        let fixedMonth = tomorrow.getMonth() + 1;
        // date.innerText = tomorrow.getDate() + "." + fixedMonth + "." + tomorrow.getFullYear();

        dateSpan.innerText = ("0" + tomorrow.getDate()).slice(-2) + "."
            + ("0" + (tomorrow.getMonth() + 1)).slice(-2) + "."
            + tomorrow.getFullYear();
        dateSpan.style.color = "cornflowerblue";

        cell.firstChild.appendChild(dateSpan);
    }
}

/**
 * Get number hours between two dates
 *
 * @param to future date
 * @param from past date
 * @returns {number} of hours between
 */
function getRangeInHours(to, from) {
    let seconds = Math.floor((to - (from)) / 1000);
    let minutes = Math.floor(seconds / 60);
    return Math.floor(minutes / 60);
}

/**
 * Fills panel with given elements
 *
 * @param rootUrl url path to create wih event info
 * @param events to fill with
 * @param dateRange of given panel
 */
function fillTimePanel(rootUrl, events, dateRange) {

    events.forEach(event => {
        console.log("placing event:", dateRange);

        let eventColor = generateEventColorForEvent(event);
        let eventBackgroundColor = "#" + eventColor;
        let eventForegroundColor = "#" + pickTextColorBasedOnBgColorSimple(eventColor, "FFFFFF", "000000");

        let from = new Date(event.fromDate);
        let fromHours = from.getHours();
        let fromMinutes = from.getMinutes() < 10 ? "0" + from.getMinutes() : from.getMinutes();

        let to = new Date(event.toDate);
        let toHours = to.getHours();
        let toMinutes = to.getMinutes() < 10 ? "0" + to.getMinutes() : to.getMinutes();

        const ZERO_MINUTES = "00";

        let hours = getRangeInHours(to, from);

        for (let i = 0; i <= hours; i++) {
            let nextHour = new Date(from);
            nextHour.setHours(nextHour.getHours() + i, nextHour.getMinutes(), nextHour.getSeconds(), nextHour.getMilliseconds());
            let hourNumber = nextHour.getHours();

            // console.log("next Hour: ", nextHour);
            let strHour = hourNumber < 10 ? '0' + hourNumber : hourNumber;

            let cellId = nextHour.getDate() + "." + (nextHour.getMonth() + 1) + "." + nextHour.getFullYear() + "-" + strHour + ":" + ZERO_MINUTES;

            createLink(rootUrl, eventBackgroundColor, eventForegroundColor, event, fromHours, fromMinutes, toHours, toMinutes, cellId);
        }
    });

}

/**
 * Generates foreground element color, based on background
 *
 * @param bgColor background color
 * @param lightColor light color
 * @param darkColor darck color
 * @returns {*} color with '#' (ex: #000000 or #FFFFFF)
 */
function pickTextColorBasedOnBgColorSimple(bgColor, lightColor, darkColor) {
    let color = (bgColor.charAt(0) === '#') ? bgColor.substring(1, 7) : bgColor;
    let r = parseInt(color.substring(0, 2), 16); // hexToR
    let g = parseInt(color.substring(2, 4), 16); // hexToG
    let b = parseInt(color.substring(4, 6), 16); // hexToB
    return (((r * 0.299) + (g * 0.587) + (b * 0.114)) > 186) ?
        darkColor : lightColor;
}