function showSection(name) {
    ['home', 'posts', 'users', 'socials'].forEach(s => {
        document.getElementById('section-' + s).style.display = s === name ? '' : 'none';
    });
    document.querySelectorAll('.nav-links a').forEach(a => {
        a.classList.toggle('active', a.dataset.section === name);
    });
    window.scrollTo(0, 0);
}

function toggleForm(id) {
    const el = document.getElementById(id);
    el.style.display = el.style.display === 'none' ? '' : 'none';
}

function notify(msg, isError) {
    let n = document.getElementById('notify-box');
    if (!n) {
        n = document.createElement('div');
        n.id = 'notify-box';
        n.style.cssText = 'position:fixed;top:76px;right:24px;z-index:9999;padding:12px 22px;border-radius:10px;font-weight:600;font-size:.9rem;box-shadow:0 4px 20px rgba(0,0,0,.15);transition:opacity .3s;min-width:220px';
        document.body.appendChild(n);
    }
    n.textContent = msg;
    n.style.background = isError ? '#dc2626' : '#7c3aed';
    n.style.color = '#fff';
    n.style.opacity = '1';
    clearTimeout(n._timer);
    n._timer = setTimeout(() => { n.style.opacity = '0'; }, 2800);
}

async function likePost(id) {
    const res = await fetch('/api/posts/' + id + '/like', { method: 'POST' });
    if (res.ok) {
        const data = await res.json();
        const el = document.getElementById('likes-' + id);
        if (el) el.textContent = data.likes + ' лайков';
        notify('Лайк поставлен!');
    } else {
        notify('Ошибка', true);
    }
}

async function deletePost(id) {
    if (!confirm('Удалить пост?')) return;
    const res = await fetch('/api/posts/' + id, { method: 'DELETE' });
    if (res.ok) {
        document.getElementById('post-card-' + id)?.remove();
        notify('Пост удалён');
    } else {
        notify('Ошибка', true);
    }
}

async function createPost() {
    const userId   = document.getElementById('post-userId').value;
    const title    = document.getElementById('post-title').value.trim();
    const content  = document.getElementById('post-content').value.trim();
    const imageUrl = document.getElementById('post-imageUrl').value.trim();
    if (!userId || !title || !content) { notify('Заполните все обязательные поля', true); return; }
    const res = await fetch('/api/posts', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId: Number(userId), title, content, imageUrl: imageUrl || null })
    });
    if (res.ok) { notify('Пост опубликован!'); location.reload(); }
    else {
        const e = await res.json();
        notify((e.messages ? e.messages[0] : e.error) || 'Ошибка', true);
    }
}

async function deleteUser(id) {
    if (!confirm('Удалить пользователя?')) return;
    const res = await fetch('/api/users/' + id, { method: 'DELETE' });
    if (res.ok) {
        document.getElementById('user-card-' + id)?.remove();
        notify('Пользователь удалён');
    } else {
        notify('Ошибка', true);
    }
}

async function createUser() {
    const username = document.getElementById('user-username').value.trim();
    const email    = document.getElementById('user-email').value.trim();
    const bio      = document.getElementById('user-bio').value.trim();
    if (!username || !email) { notify('Заполните имя и email', true); return; }
    const res = await fetch('/api/users', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, email, bio })
    });
    if (res.ok) { notify('Пользователь создан!'); location.reload(); }
    else {
        const e = await res.json();
        notify((e.messages ? e.messages[0] : e.error) || 'Ошибка', true);
    }
}

async function deleteSocial(id) {
    if (!confirm('Удалить соцсеть?')) return;
    const res = await fetch('/api/social-media/' + id, { method: 'DELETE' });
    if (res.ok) {
        document.getElementById('social-card-' + id)?.remove();
        notify('Соцсеть удалена');
    } else {
        notify('Ошибка', true);
    }
}

async function createSocial() {
    const userId     = document.getElementById('social-userId').value;
    const platform   = document.getElementById('social-platform').value;
    const profileUrl = document.getElementById('social-profileUrl').value.trim();
    const followers  = document.getElementById('social-followers').value;
    if (!userId || !platform || !profileUrl) { notify('Заполните все обязательные поля', true); return; }
    const res = await fetch('/api/social-media', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId: Number(userId), platform, profileUrl, followers: Number(followers) || 0 })
    });
    if (res.ok) { notify('Соцсеть привязана!'); location.reload(); }
    else {
        const e = await res.json();
        notify((e.messages ? e.messages[0] : e.error) || 'Ошибка', true);
    }
}

