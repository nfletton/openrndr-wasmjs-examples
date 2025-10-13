import {getSketchData, getNavigationLinks} from './wasmjsExamples.mjs';

(() => {


    let nav;
    let links;
    let activeLink = null;

    let btnPrev;
    let btnNext;

    let btnToggle;


    let canvas = null;
    let ctx = null;
    let contentEl = null;
    let dpr = 1;

    function setNavGroupOpenState(targetGroup) {
        let groups = nav.querySelectorAll('details.group')
        groups.forEach(g => {
            const shouldOpen = g === targetGroup;
            g.open = shouldOpen;
            g.setAttribute('aria-expanded', String(shouldOpen));
        });
    }

    function setActiveNavItem(activeNavId) {
        let newActiveNav = nav.querySelector(`#${activeNavId}`);
        if (!newActiveNav) return;

        let currentActiveNav = nav.querySelector('.active');
        if (currentActiveNav) {
            currentActiveNav.classList.remove('active');
            currentActiveNav.setAttribute('aria-current', 'false');
        }
        newActiveNav.classList.add('active');
        newActiveNav.setAttribute('aria-current', 'page');
        const group = newActiveNav.closest('details.group');
        if (group) setNavGroupOpenState(group);
        return newActiveNav
    }


    function stepActiveNavItem(delta) {
        if (!links.length) return;
        const idx = activeLink ? links.indexOf(activeLink) : -1;
        const targetIdx = (idx + delta) < 0 ? links.length - 1 : (idx + delta) % links.length;
        const nextActiveNavId = links[targetIdx].id;
        if (nextActiveNavId) activeLink = setActiveNavItem(nextActiveNavId);
        activeLink.click()
    }

    function debounce(func, delay = 100) {
        let timer;
        return function (...args) {
            clearTimeout(timer);
            console.log("debounce");
            timer = setTimeout(() => func.apply(this, args), delay);
        };
    }

    function init() {
        let data = getSketchData()

        nav = document.querySelector('.nav');
        nav.appendChild(getNavigationLinks())

        // groups = Array.from(nav.querySelectorAll('details.group'));
        links = Array.from(nav.querySelectorAll('a[href]'));

        btnPrev = document.getElementById('navPrev');
        btnNext = document.getElementById('navNext');
        btnToggle = document.getElementById('toggleSidebar');

        let activeNavId = sessionStorage.getItem('activeNav');

        if (activeNavId) activeLink = setActiveNavItem(activeNavId);


        // Header nav buttons
        btnPrev?.addEventListener('click', () => stepActiveNavItem(-1));
        btnNext?.addEventListener('click', () => stepActiveNavItem(1));

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