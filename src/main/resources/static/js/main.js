/* ═══════════════════════════════════════════════════════════════
   PetConnect — Main JavaScript
   Global utilities, toast, animations, search, AJAX helpers
═══════════════════════════════════════════════════════════════ */

// ── TOAST NOTIFICATION ───────────────────────────────────────────
function showToast(msg, type = 'default') {
  let t = document.getElementById('pc-toast');
  if (!t) {
    t = document.createElement('div');
    t.id = 'pc-toast';
    t.className = 'pc-toast';
    document.body.appendChild(t);
  }
  const colors = {
    success: 'linear-gradient(135deg,#43A047,#00BFA5)',
    error:   'linear-gradient(135deg,#E53935,#EC407A)',
    info:    'linear-gradient(135deg,#1E88E5,#7C3AED)',
    default: 'linear-gradient(135deg,#FF6F00,#EC407A)',
  };
  t.style.background = colors[type] || colors.default;
  t.textContent = msg;
  t.classList.add('show');
  setTimeout(() => t.classList.remove('show'), 3200);
}

// ── CONFIRM ADOPTION (live page action) ──────────────────────────
function confirmAdoption(petId, petName) {
  if (confirm(`Adopt ${petName}? You'll be redirected to complete the process.`)) {
    fetch(`/api/pets/${petId}`)
      .then(r => r.json())
      .then(pet => {
        if (pet.status === 'AVAILABLE') {
          showToast(`🐾 Adoption request sent for ${petName}!`, 'success');
          setTimeout(() => window.location = '/dashboard', 1500);
        } else {
          showToast('⚠️ This pet is no longer available.', 'error');
        }
      })
      .catch(() => showToast('🐾 Please login to adopt a pet!', 'info'));
  }
}

// ── SEARCH TYPEAHEAD ─────────────────────────────────────────────
let searchTimeout = null;
function initSearch() {
  const input = document.querySelector('.hero-search-input');
  if (!input) return;
  input.addEventListener('input', () => {
    clearTimeout(searchTimeout);
    searchTimeout = setTimeout(() => {
      const q = input.value.trim();
      if (q.length < 2) return;
      fetch(`/api/pets/search?q=${encodeURIComponent(q)}`)
        .then(r => r.json())
        .then(pets => {
          // Could render a dropdown here — keeping minimal for now
          console.log('Search results:', pets.length, 'pets found');
        })
        .catch(() => {});
    }, 350);
  });
}

// ── SCROLL REVEAL (Intersection Observer) ───────────────────────
function initReveal() {
  const io = new IntersectionObserver((entries) => {
    entries.forEach(e => {
      if (e.isIntersecting) {
        e.target.classList.add('revealed');
        io.unobserve(e.target);
      }
    });
  }, { threshold: 0.12 });
  document.querySelectorAll('.pet-card, .step-card, .match-form-card, .med-panel, .enroll-card, .admin-panel, .dash-stat-card')
    .forEach(el => {
      el.classList.add('reveal-ready');
      io.observe(el);
    });
}

// ── NAVBAR SCROLL SHADOW ─────────────────────────────────────────
function initNavScroll() {
  const nav = document.querySelector('.pc-navbar');
  if (!nav) return;
  window.addEventListener('scroll', () => {
    nav.style.boxShadow = window.scrollY > 10
      ? '0 4px 20px rgba(0,0,0,0.10)'
      : 'none';
  }, { passive: true });
}

// ── PET CARD HOVER SPARKLE ───────────────────────────────────────
function initCardSparkle() {
  document.querySelectorAll('.pet-card').forEach(card => {
    card.addEventListener('mouseenter', (e) => {
      const sparkle = document.createElement('div');
      sparkle.textContent = '✨';
      sparkle.style.cssText = `
        position:absolute;pointer-events:none;font-size:16px;
        top:${Math.random()*60+10}px;left:${Math.random()*60+10}px;
        animation:sparkleAnim .6s ease forwards;z-index:10;
      `;
      card.style.position = 'relative';
      card.appendChild(sparkle);
      setTimeout(() => sparkle.remove(), 600);
    });
  });
}

// ── SMOOTH COUNTER ANIMATION ─────────────────────────────────────
function animateCounters() {
  document.querySelectorAll('.hstat-num, .as-num, .dsc-num').forEach(el => {
    const text = el.textContent.replace(/[^0-9]/g, '');
    const target = parseInt(text, 10);
    if (!target || target < 2) return;
    const suffix = el.textContent.replace(/[0-9]/g, '');
    let current = 0;
    const step = Math.ceil(target / 40);
    const timer = setInterval(() => {
      current = Math.min(current + step, target);
      el.textContent = current + suffix;
      if (current >= target) clearInterval(timer);
    }, 35);
  });
}

// ── ENROLL PREVIEW ───────────────────────────────────────────────
function initEnrollPreview() {
  const zone = document.getElementById('upload-zone');
  const input = document.getElementById('pet-photo');
  const preview = document.getElementById('img-preview');
  if (!zone || !input) return;

  zone.addEventListener('click', () => input.click());

  // Drag-and-drop
  zone.addEventListener('dragover', e => { e.preventDefault(); zone.style.borderColor = '#FF6F00'; });
  zone.addEventListener('dragleave', () => { zone.style.borderColor = ''; });
  zone.addEventListener('drop', e => {
    e.preventDefault();
    zone.style.borderColor = '';
    const file = e.dataTransfer.files[0];
    if (file && file.type.startsWith('image/')) previewFile(file);
  });

  input.addEventListener('change', () => {
    if (input.files[0]) previewFile(input.files[0]);
  });

  function previewFile(file) {
    const r = new FileReader();
    r.onload = e => {
      if (preview) { preview.src = e.target.result; preview.style.display = 'block'; }
    };
    r.readAsDataURL(file);
  }
}

// ── INJECT SPARKLE KEYFRAMES ─────────────────────────────────────
function injectStyles() {
  const s = document.createElement('style');
  s.textContent = `
    @keyframes sparkleAnim{0%{opacity:1;transform:scale(1) translateY(0)}100%{opacity:0;transform:scale(.5) translateY(-20px)}}
    .reveal-ready{opacity:0;transform:translateY(20px);transition:opacity .5s ease,transform .5s ease;}
    .revealed{opacity:1;transform:translateY(0);}
  `;
  document.head.appendChild(s);
}

// ── INIT ON DOM READY ────────────────────────────────────────────
document.addEventListener('DOMContentLoaded', () => {
  injectStyles();
  initNavScroll();
  initReveal();
  initSearch();
  initCardSparkle();
  initEnrollPreview();

  // Delay counter animation so it runs after reveal
  setTimeout(animateCounters, 400);

  // Bootstrap tooltips
  if (typeof bootstrap !== 'undefined') {
    document.querySelectorAll('[data-bs-toggle="tooltip"]')
      .forEach(el => new bootstrap.Tooltip(el));
  }
});
