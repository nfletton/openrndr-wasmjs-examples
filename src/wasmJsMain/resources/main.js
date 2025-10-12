import {getNavigationLinks} from './wasmjsExamples.mjs';
(() => {


    let nav;
    let groups;
    let links;
    let btnPrev;
    let btnNext;
    let btnToggle;

    let activeLink = null;
    let canvas = null;
    let ctx = null;
    let contentEl = null;
    let dpr = 1;

    function setGroupOpenState(targetGroup) {
        groups.forEach(g => {
            const shouldOpen = g === targetGroup;
            g.open = shouldOpen;
            g.setAttribute('aria-expanded', String(shouldOpen));
        });
    }

    function updateHeaderNavButtons() {
        if (!btnPrev || !btnNext) return;
        const all = links;
        const idx = activeLink ? all.indexOf(activeLink) : -1;
        const hasItems = all.length > 0;
        btnPrev.disabled = !hasItems || idx <= 0;
        btnNext.disabled = !hasItems || idx === -1 || idx >= all.length - 1;
    }

    function setActive(newLink, {expandGroup = true} = {}) {
        if (!newLink) return;

        if (activeLink) {
            activeLink.classList.remove('active');
            activeLink.setAttribute('aria-current', 'false');
        }

        activeLink = newLink;
        activeLink.classList.add('active');
        activeLink.setAttribute('aria-current', 'page');

        if (expandGroup) {
            const group = activeLink.closest('details.group');
            if (group) setGroupOpenState(group);
        }

        // Keep header buttons in sync
        // updateHeaderNavButtons();
    }


    function step(delta) {
        if (!links.length) return;
        const idx = activeLink ? links.indexOf(activeLink) : -1;
        let targetIdx = (idx + delta) < 0 ? links.length - 1 : (idx + delta) % links.length;
        const target = links[targetIdx];
        if (target) setActive(target, {expandGroup: true});
        target.click()
    }

    function debounce(func, delay = 100) {
        let timer;
        return function(...args) {
            clearTimeout(timer);
            console.log("debounce");
            timer = setTimeout(() => func.apply(this, args), delay);
        };
    }

    function init() {
        nav = document.querySelector('.nav');
        let x =getNavigationLinks()
        nav.appendChild(x)
        // Cache DOM references now that the DOM is ready

        groups = Array.from(nav.querySelectorAll('details.group'));
        links = Array.from(nav.querySelectorAll('a[href]'));
        btnPrev = document.getElementById('navPrev');
        btnNext = document.getElementById('navNext');
        btnToggle = document.getElementById('toggleSidebar');

        // Initialize active state: default to first link
        const urlParams = new URLSearchParams(window.location.search);
        const initialLink = links[urlParams.get("id")] || links[0];
        setActive(initialLink, {expandGroup: true});


        groups.forEach(g => {
            g.addEventListener('toggle', () => {
                if (g.open) {
                    setGroupOpenState(g);
                }
            });
        });

        // Header nav buttons
        btnPrev?.addEventListener('click', () => step(-1));
        btnNext?.addEventListener('click', () => step(1));

        // Sidebar toggle
        btnToggle?.addEventListener('click', () => {
            const collapsed = !document.body.classList.contains('nav-collapsed');
            // If about to collapse, move focus away from nav to safer target
            if (collapsed && document.activeElement && nav.contains(document.activeElement)) {
                btnToggle.focus();
            }
            document.body.classList.toggle('nav-collapsed', collapsed);
            btnToggle.setAttribute('aria-pressed', String(collapsed));
            nav.setAttribute('aria-hidden', String(collapsed));
            // Update button title/icon for clarity
            btnToggle.title = collapsed ? 'Expand sidebar' : 'Collapse sidebar';
            btnToggle.setAttribute('aria-label', btnToggle.title);
        });

/*

        // Canvas sizing: make the canvas fill the right panel and resize with window/panel
        contentEl = document.querySelector('.content');
        canvas = document.getElementById('openrndr-canvas');
        canvas.removeAttribute('width');
        canvas.removeAttribute('height');
        if (contentEl && canvas instanceof HTMLCanvasElement) {
            ctx = canvas.getContext('2d');
            const resize = (entries, observer) => {
                console.log("resize");
                const rect = contentEl.getBoundingClientRect();
                dpr = Math.max(1, window.devicePixelRatio || 1);
                // Set the canvas drawing buffer size
                const w = Math.max(0, Math.floor(rect.width * dpr));
                const h = Math.max(0, Math.floor(rect.height * dpr));
                // if (canvas.width !== w || canvas.height !== h) {
                //     canvas.width = w;
                //     canvas.height = h;
                // }
                // Ensure CSS size continues to fill the parent
                // canvas.width = rect.width;
                // canvas.height = rect.height;
            };
            canvas.removeAttribute('width');
            canvas.removeAttribute('height');

            // Resize on window and on element size changes (sidebar drag)
            const ro = new ResizeObserver(debounce(resize, 300));
            ro.observe(contentEl);
            // window.addEventListener('resize', resize);
            // Initial sizing
            // resize();
        }
*/


        /*
                // Ensure header buttons reflect current state on load
                updateHeaderNavButtons();
        */
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }
})();