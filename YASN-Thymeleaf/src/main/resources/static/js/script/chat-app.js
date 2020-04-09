'use strict';

window.emojioneVersion = "3.1.2";
$(document).ready(function () {
    $("#message").emojioneArea({
        buttonTitle: "Use the TAB key to insert emoji faster",
        recentEmojies: true,
        pickerPosition: "right",
        filtersPosition: "top",
        hidePickerOnBlur: true,
        search: false
    });
});

let usernameForm = document.querySelector('#usernameForm');
let messageForm = document.querySelector('#messageForm');
let messageInput = document.querySelector('#message');
let messageArea = document.querySelector('#messageArea');
let connectingElement = document.querySelector('.connecting');

let stompClient = null;
let username = null;

connect();

function connect(event) {
    username = document.querySelector('#name').value.trim();
    let socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}

function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    );

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}

function sendMessage(event) {
    let avatarPictureURL = document.querySelector('#chat-avatar-pic').value;

    let messageContent = messageInput.value.trim();

    if (messageContent && stompClient) {
        let chatMessage = {
            sender: username,
            avatarPictureURL: avatarPictureURL,
            content: messageInput.value,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));


        messageInput.value = '';
        document.querySelector('.emonionearea-editor').textContent = '';
    }

    event.preventDefault();
}



function onMessageReceived(payload) {
    let message = JSON.parse(payload.body);
    let messageElement = document.createElement('li');
    let avatarPictureUrl = message.avatarPictureURL;

    let avatarPicture = document.createElement('img');
    avatarPicture.classList.add('chat-avatar-picture');
    avatarPicture.setAttribute('src', avatarPictureUrl);

    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        let avatarElement = document.createElement('i');
        avatarElement.appendChild(avatarPicture);
        messageElement.appendChild(avatarElement);

        let usernameElement = document.createElement('span');
        let usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    let textElement = document.createElement('p');
    let messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);

