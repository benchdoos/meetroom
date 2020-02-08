const WEEKDAY = {"MONDAY": 1, "TUESDAY": 2, "WEDNESDAY": 3, "THURSDAY": 4, "FRIDAY": 5};
const BGCOLOR = ["#00FFFF", "#7FFFD4", "#0000FF", "#8A2BE2", "#A52A2A", "#DEB887", "#5F9EA0", "#6495ED", "#FF1493"];
const FONTCOLOR = ["#000000", "#000000", "#7FFFD4", "#00FFFF", "#00FFFF", "#A52A2A", "#000000", "#7FFF00", "#FFE4C4"];
const DAYS = ['Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'];

/**
 * 예약하기 모달 띄우기
 */
$('#create-reservation-modal').on('shown.bs.modal', function () {
    $('#create-reservation').trigger('focus')
});

/**
 * 회의실 생성 모달 띄우기
 */
$('#create-meetingroom-modal').on('shown.bs.modal', function () {
    $('#create-meetingroom').trigger('focus')
});

/**
 * 회의실 생성버튼 클릭
 */
// $("#cmr-save").on("click", function () {
//     var roomName = $("#cmr-name").val();
//
//     var postData = {
//         roomName: roomName,
//     };
//
//     $.ajax({
//         url: "/room",
//         type: "POST",
//         data: JSON.stringify(postData),
//         contentType: "application/json; charset=utf-8",
//         dataType: "json",
//     }).always(function (response) {
//         if (response && response.code == "0000") {
//             drawMeetingRoom();
//         } else {
//             alert(response.responseJSON.message);
//         }
//     });
// });

/**
 * 예약 생성버튼 클릭
 */
$("#cr-save").on("click", function () {
    var postData = {
        memberName: $("#cr-member").val(),
        roomName: $("#cr-room").val(),
        date: $("#cr-date").val(),
        time: $("#cr-time").val(),
        repeat: $("#cr-repeat").val(),
        comment: $("#cr-comment").val()
    };

    $.ajax({
        url: "/reservation",
        type: "POST",
        data: JSON.stringify(postData),
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    }).always(function (response) {
        if (response && response.code == "0000") {
            clearTimeScheduleTable();
            drawTimePanel();
            getReservationsByRoomName(selectedRoom.roomName);
        } else {
            alert(response.responseJSON.message);
        }
    });
});

/**
 * 예약 스케쥴 테이블 내용 삭제
 */
function clearTimeScheduleTable() {
    var table = document.getElementById("timetable");
    while (table.hasChildNodes()) {
        table.removeChild(table.firstChild);
    }
}

/**
 * Draw time panel
 */
function drawTimePanel() {
    var table = document.getElementById("timetable");
    var head = table.insertRow(0);
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

    var hour = 0;
    var min = 0;
    const MAXIMUM_DAYS = 7;
    const MAXIMUM_HOURS = 23;
    for (var i = 0; i <= MAXIMUM_HOURS; i++) {
        var row = table.insertRow(i + 1);
        for (var j = 0; j <= MAXIMUM_DAYS; j++) {
            var cell = row.insertCell(j);
            // min = i % 2 !== 0 ? 30 : 0;
            var strHour = hour < 10 ? '0' + hour : hour;
            var strMin = min < 10 ? '0' + min : min;
            if (j === 0) {
                cell.innerText = strHour + ":" + strMin;
            } else {
                cell.setAttribute("class", "eventCell");
            }
            cell.setAttribute("id", strHour.toString() + strMin.toString() + j.toString());
            cell.width = 100;
        }
        hour++;
    }
}

function createLink(rootUrl, eventBackgroundColor, eventForegroundColor, event, fromHours, fromMinutes, toHours, toMinutes, elementId) {
    let element = document.createElement("a");
    element.setAttribute("style",
        "background-color:" + eventBackgroundColor + "; " +
        "border-color: " + shadeColor(eventBackgroundColor,100) + "; " +
        "color: " + eventForegroundColor + "; " +
        "margin: 2px;");
    element.setAttribute("data-toggle", "tooltip");
    element.setAttribute("data-placement", "top");
    element.setAttribute("class", "btn btn-primary");
    element.setAttribute("title", "User: " + event.user.firstName + " " + event.user.lastName);
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


function shadeColor(color, percent) {

    var R = parseInt(color.substring(1,3),16);
    var G = parseInt(color.substring(3,5),16);
    var B = parseInt(color.substring(5,7),16);

    R = parseInt(R * (100 + percent) / 100);
    G = parseInt(G * (100 + percent) / 100);
    B = parseInt(B * (100 + percent) / 100);

    R = (R<255)?R:255;
    G = (G<255)?G:255;
    B = (B<255)?B:255;

    var RR = ((R.toString(16).length==1)?"0"+R.toString(16):R.toString(16));
    var GG = ((G.toString(16).length==1)?"0"+G.toString(16):G.toString(16));
    var BB = ((B.toString(16).length==1)?"0"+B.toString(16):B.toString(16));

    return "#"+RR+GG+BB;
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

function fillDays(range) {
    let from = new Date(range.fromDate);
    console.log(">>>");
    for (let i = 1; i <= 7; i++) {

        console.log("cell:", i, from);

        const tomorrow = new Date(from);
        tomorrow.setDate(tomorrow.getDate() + (i - 1));

        let table = document.getElementById("timetable");
        let cell = table.rows[0].cells[i];


        let dateSpan = document.createElement("span");
        let fixedMonth = tomorrow.getMonth() + 1;
        // date.innerText = tomorrow.getDate() + "." + fixedMonth + "." + tomorrow.getFullYear();

        const fixedDate = ("0" + tomorrow.getDate()).slice(-2) + "."
            + ("0" + (tomorrow.getMonth() + 1)).slice(-2) + "."
            + tomorrow.getFullYear();

        dateSpan.innerText = fixedDate;
        dateSpan.style.color = "cornflowerblue";

        cell.firstChild.appendChild(dateSpan);
    }
}

/**
 * Fills panel with given elements
 *
 * @param events to fill with
 * @param dateRange of given panel
 */
function fillTimePanel(rootUrl, events, dateRange) {
    function fixFirstCellIndex(j) {
        return j < 10 ? "0" + j : j;
    }

    events.forEach(event => {
        console.log("placing event:", event, dateRange);

        let eventColor = generateEventColorForEvent(event);
        let eventBackgroundColor = "#" + eventColor;
        let eventForegroundColor = "#" + pickTextColorBasedOnBgColorSimple(eventColor, "FFFFFF", "000000");

        let from = new Date(event.fromDate);
        let fromHours = from.getHours();
        let fromMinutes = from.getMinutes() < 10 ? "0" + from.getMinutes() : from.getMinutes();
        let fromDay = from.getDay();
        let fromDayNumber = from.getDate();

        let to = new Date(event.toDate);
        let toDay = to.getDay();
        let toHours = to.getHours();
        let toMinutes = to.getMinutes() < 10 ? "0" + to.getMinutes() : to.getMinutes();
        let toDayNumber = to.getDate();

        const ZERO_MINUTES = "00";

        console.log("event date: ", fromDayNumber, toDayNumber);

        if (fromDayNumber !== toDayNumber) {
            for (let i = 0; i < (toDayNumber - fromDayNumber) + 1; i++) {
                let indexFrom = fromDayNumber + i - new Date(dateRange.fromDate).getDate();
                console.log("indexFrom: ", indexFrom);
                if (indexFrom >= 0 && indexFrom <= 7) {
                    if (fromDayNumber + i === fromDayNumber) {
                        for (let j = fromHours; j <= 23; j++) {
                            let id = fixFirstCellIndex(j) + ZERO_MINUTES + (indexFrom + 1);
                            createLink(rootUrl, eventBackgroundColor, eventForegroundColor, event, fromHours, fromMinutes, toHours, toMinutes, id);
                        }
                    } else if (fromDayNumber + i === toDayNumber) {
                        for (let j = 0; j <= toHours; j++) {
                            let id = fixFirstCellIndex(j) + ZERO_MINUTES + (indexFrom + 1);
                            createLink(rootUrl, eventBackgroundColor, eventForegroundColor, event, fromHours, fromMinutes, toHours, toMinutes, id);
                        }
                    }
                } else {
                    let indexTo = toDayNumber + i - new Date(dateRange.fromDate).getDate();
                    console.log("indexTo: ", indexTo);
                    if (indexTo >= 0 && indexTo <= 7) {
                        for (let j = 0; j <= toHours; j++) {
                            let id = fixFirstCellIndex(j) + ZERO_MINUTES + (indexTo + 1);
                            createLink(rootUrl, eventBackgroundColor, eventForegroundColor, event, fromHours, fromMinutes, toHours, toMinutes, id);
                        }
                    } else {
                        let indexFixed = 7 - i;
                        console.log("indexFixed: ", indexFixed);
                        for (let j = fromHours; j <= 23; j++) {
                            let id = fixFirstCellIndex(j) + ZERO_MINUTES + (indexFixed + 1);
                            createLink(rootUrl, eventBackgroundColor, eventForegroundColor, event, fromHours, fromMinutes, toHours, toMinutes, id);
                        }
                    }

                }
            }
        } else {
            console.log("ELSE: ", fromDayNumber, toDayNumber ); //todo fixme отображение корявое
            for (var j = fromHours; j <= toHours; j++) {
                if (toMinutes !== ZERO_MINUTES) {
                    let id = fixFirstCellIndex(j) + ZERO_MINUTES + fromDay;
                    createLink(rootUrl, eventBackgroundColor, eventForegroundColor, event, fromHours, fromMinutes, toHours, toMinutes, id);
                } else {
                    if (j !== toHours) { //prevents duplicates to next hour
                        let id = fixFirstCellIndex(j) + ZERO_MINUTES + fromDay;
                        createLink(rootUrl, eventBackgroundColor, eventForegroundColor, event, fromHours, fromMinutes, toHours, toMinutes, id);
                    }
                }
            }
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

/*
* 회의실에 예약된 방 조회.
* */
var selectedRoom = {};

function getReservationsByRoomName(name, li) {
    document.querySelectorAll(".list-group-item").forEach(r => {
        if (r.innerHTML == name) {
            $(r).css("background-color", "#DAA520");
        } else {
            $(r).css("background-color", "#FFFFFF");
        }
    });

    selectedRoom.roomName = name;
    clearTimeScheduleTable();
    drawTimePanel();

    const today = $("#today").text();

    $.get("/reservations/" + name + "?date=" + today, function (result) {
        var colorIndex = Math.floor(Math.random() * BGCOLOR.length);
        Array.from(result).forEach(r => {
            var comment = r.comment;
            var weekOfDay = WEEKDAY[r.dayOfWeek];
            var from = r.reservedTimeFrom;
            var to = r.reservedTimeTo;
            var user = r.member.name;
            var repeatSeq = r.repeatSeq;
            var repeatTotal = r.repeatTotal;
            var repeatText = "";
            if (repeatTotal > 1) {
                repeatText = "<br/>[반복 " + (repeatSeq + 1) + "회 /" + repeatTotal + "]";
            }
            $("#" + from + weekOfDay).html(comment + "<br/>(" + user + ")" + repeatText);
            colorIndex = colorIndex % (BGCOLOR.length - 1);
            colorIndex++;

            for (let i = from; i <= to;) {
                let id = "#" + i + weekOfDay;
                $(id).css("background-color", BGCOLOR[colorIndex]);
                $(id).css("color", FONTCOLOR[colorIndex]);
                $(id).css("font-size", "12px");
                $(id).css("text-align", "center");
                $(id).attr("data-from", from);
                $(id).attr("data-to", to);
                $(id).on("click", function () {
                    popupReservationDialog($(this).attr("id"))
                });

                let increase = parseInt(i) + 30;
                if ((increase % 100) === 60) {
                    increase = increase - 60 + 100;
                }
                i = parseInt(increase);
                i = i < 1000 ? '0' + i : i;
            }
        });
    });
}

/**
 * 예약된 방 수정을 위한 다이얼로그 띄우기
 */
function popupReservationDialog(tdId) {
    //ex) tdId=12303 = 12시30분, 수(3)
    $('#update-reservation-modal').modal('show');

    var yyyyMMddMonday = $("#hidden-monday").text();
    var mon = new Date(yyyyMMddMonday.substring(0, 4) + "-" + yyyyMMddMonday.substring(4, 6) + "-" + yyyyMMddMonday.substring(6));
    var weekOfDay = tdId.substring(4);
    var targetDate = new Date();
    targetDate.setDate(mon.getDate() + (parseInt(weekOfDay) - 1));
    var fromTime = $("#" + tdId).data().from;
    var toTime = $("#" + tdId).data().to;

    $.get("/reservations/" + selectedRoom.roomName + "/" + targetDate.yyyymmdd() + "/" + fromTime + "/" + toTime, function (result) {
        $("#hidden-reservation-id").text(result.reservationId);
        $("#ur-date").val(result.reservedDate);
        $("#ur-time").val(result.reservedTimeFrom + "~" + result.reservedTimeTo);
        $("#ur-comment").val(result.comment);
    });
}

/**
 * 예약된 방 삭제 버튼을 눌렀을 때.
 */
$("#ur-delete").on("click", function () {
    var reservationId = $("#hidden-reservation-id").text();

    $.ajax({
        url: "/reservation/" + reservationId,
        type: "DELETE",
        data: JSON.stringify(reservationId),
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    }).always(function (response) {
        if (response && response.code == "0000") {
            clearTimeScheduleTable();
            drawTimePanel();
            getReservationsByRoomName(selectedRoom.roomName);
        } else {
            alert(response.responseJSON.message);
        }
    });
});

/**
 * 예약된 방 수정 버튼을 눌렀을 때
 */
$("#ur-save").on("click", function () {
    var reservationId = $("#hidden-reservation-id").text();
    var postData = {
        reservedDate: $("#ur-date").val(),
        reservedTime: $("#ur-time").val(),
        comment: $("#ur-comment").val()
    };

    $.ajax({
        url: "/reservation/" + reservationId,
        type: "PUT",
        data: JSON.stringify(postData),
        contentType: "application/json; charset=utf-8",
        dataType: "json"
    }).always(function (response) {
        if (response && response.code == "0000") {
            clearTimeScheduleTable();
            drawTimePanel();
            getReservationsByRoomName(selectedRoom.roomName);
        } else {
            alert(response.responseJSON.message);
        }
    });
});

Date.prototype.yyyymmdd = function () {
    var mm = this.getMonth() + 1;
    var dd = this.getDate();

    return [this.getFullYear(),
        (mm > 9 ? '' : '0') + mm,
        (dd > 9 ? '' : '0') + dd
    ].join('');
};
