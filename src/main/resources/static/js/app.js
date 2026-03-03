function openModal(id) {
    document.getElementById(id).classList.add('open');
}

function closeModal(id) {
    document.getElementById(id).classList.remove('open');
}

function closeModalOutside(e, id) {
    if (e.target.id === id) closeModal(id);
}

function showToast(msg, type) {
    var container = document.getElementById('toast-container');
    var toast = document.createElement('div');
    toast.className = 'toast ' + (type || '');
    toast.textContent = msg;
    container.appendChild(toast);
    setTimeout(function() { toast.remove(); }, 3500);
}

function createPost() {
    var title = document.getElementById('post-title').value.trim();
    var content = document.getElementById('post-content').value.trim();
    var userId = parseInt(document.getElementById('post-userId').value);

    if (!title || !content) {
        showToast('Заполните заголовок и содержание', 'error');
        return;
    }

    fetch('/api/posts', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ title: title, content: content, userId: userId })
    })
    .then(function(r) { return r.json(); })
    .then(function(post) {
        closeModal('modal-post');
        document.getElementById('post-title').value = '';
        document.getElementById('post-content').value = '';
        document.getElementById('post-userId').value = '1';
        var grid = document.getElementById('posts-grid');
        var card = document.createElement('div');
        card.className = 'post-card';
        card.setAttribute('data-id', post.id);
        card.innerHTML =
            '<div class="post-header">' +
                '<div class="post-avatar">' + post.title.charAt(0) + '</div>' +
                '<div>' +
                    '<div class="post-author">Пользователь #' + post.userId + '</div>' +
                    '<div class="post-date">Только что</div>' +
                '</div>' +
            '</div>' +
            '<h3 class="post-title">' + escHtml(post.title) + '</h3>' +
            '<p class="post-content">' + escHtml(post.content.substring(0, 130)) + '</p>' +
            '<div class="post-footer">' +
                '<button class="post-likes" onclick="likePost(' + post.id + ', this)">Лайков: <b>' + post.likes + '</b></button>' +
                '<button class="btn-sm" onclick="deletePost(' + post.id + ')">Удалить</button>' +
            '</div>';
        grid.prepend(card);
        showToast('Пост опубликован!', 'success');
    })
    .catch(function() { showToast('Ошибка при создании поста', 'error'); });
}

function likePost(id, btn) {
    fetch('/api/posts/' + id + '/like', { method: 'POST' })
    .then(function(r) { return r.json(); })
    .then(function(post) {
        var b = btn.querySelector('b');
        if (b) b.textContent = post.likes;
        showToast('Лайк поставлен!', 'success');
    })
    .catch(function() { showToast('Ошибка', 'error'); });
}

function deletePost(id) {
    if (!confirm('Удалить пост?')) return;
    fetch('/api/posts/' + id, { method: 'DELETE' })
    .then(function(r) {
        if (r.ok) {
            var card = document.querySelector('[data-id="' + id + '"]');
            if (card) {
                card.style.opacity = '0';
                card.style.transform = 'scale(0.95)';
                card.style.transition = '0.3s ease';
                setTimeout(function() { card.remove(); }, 300);
            } else {
                location.reload();
            }
            showToast('Пост удалён', 'success');
        }
    })
    .catch(function() { showToast('Ошибка при удалении', 'error'); });
}

function createUser() {
    var name = document.getElementById('user-name').value.trim();
    var email = document.getElementById('user-email').value.trim();
    var bio = document.getElementById('user-bio').value.trim();

    if (!name || !email) {
        showToast('Заполните имя и email', 'error');
        return;
    }

    fetch('/api/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: name, email: email, bio: bio })
    })
    .then(function(r) {
        if (!r.ok) return r.json().then(function(e) { throw e; });
        return r.json();
    })
    .then(function(user) {
        closeModal('modal-user');
        document.getElementById('user-name').value = '';
        document.getElementById('user-email').value = '';
        document.getElementById('user-bio').value = '';
        var grid = document.getElementById('users-grid');
        var card = document.createElement('div');
        card.className = 'user-card';
        card.innerHTML =
            '<div class="user-avatar">' + user.name.charAt(0) + '</div>' +
            '<div class="user-info">' +
                '<div class="user-name">' + escHtml(user.name) + '</div>' +
                '<div class="user-email">' + escHtml(user.email) + '</div>' +
                '<div class="user-bio">' + escHtml(user.bio || '') + '</div>' +
            '</div>';
        grid.appendChild(card);
        showToast('Пользователь создан!', 'success');
    })
    .catch(function(e) {
        var msg = typeof e === 'object' ? JSON.stringify(e) : 'Ошибка при создании пользователя';
        showToast(msg, 'error');
    });
}

function createSocial() {
    var platform = document.getElementById('social-platform').value;
    var profileUrl = document.getElementById('social-url').value.trim();
    var userId = parseInt(document.getElementById('social-userId').value);

    if (!profileUrl) {
        showToast('Введите ссылку на профиль', 'error');
        return;
    }

    fetch('/api/social-media', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ platform: platform, profileUrl: profileUrl, userId: userId })
    })
    .then(function(r) { return r.json(); })
    .then(function(sm) {
        closeModal('modal-social');
        document.getElementById('social-url').value = '';
        document.getElementById('social-userId').value = '1';
        var grid = document.getElementById('social-grid');
        var card = document.createElement('div');
        card.className = 'social-card';
        card.innerHTML =
            '<div class="social-platform">' + escHtml(sm.platform) + '</div>' +
            '<div class="social-info">' +
                '<div class="social-user">Пользователь #' + sm.userId + '</div>' +
                '<a class="social-url" href="' + escHtml(sm.profileUrl) + '" target="_blank">' + escHtml(sm.profileUrl) + '</a>' +
            '</div>';
        grid.appendChild(card);
        showToast('Соцсеть добавлена!', 'success');
    })
    .catch(function() { showToast('Ошибка при добавлении', 'error'); });
}

function escHtml(str) {
    if (!str) return '';
    return String(str)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;');
}

document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        ['modal-post', 'modal-user', 'modal-social'].forEach(function(id) {
            closeModal(id);
        });
    }
});

