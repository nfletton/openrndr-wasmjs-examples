import {getSketchData, runSketch} from './wasmjsExamples.mjs';

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

    function getNavigationLinks(sketchData) {
        const nav = document.createElement('div');
        nav.className = 'groups';
        nav.setAttribute('role', 'tree');

        Object.keys(sketchData).forEach(groupName => {
            let groupData = sketchData[groupName];
            const details = document.createElement('details');
            details.className = 'group';
            details.setAttribute('role', 'treeitem');
            details.open = false;
            details.setAttribute('aria-expanded', 'false');

            const summary = document.createElement('summary');
            summary.textContent = groupName;
            details.appendChild(summary);

            const ul = document.createElement('ul');
            ul.id = groupName;
            ul.setAttribute('role', 'group');

            groupData.forEach((sketch) => {
                const li = document.createElement('li');
                const link = document.createElement('a');
                link.href = '#';
                link.id = sketch["funcId"];
                link.textContent = sketch["navTitle"];

                link.onclick = (event) => {
                    event.preventDefault()
                    console.log(`clicked on ${sketch["navTitle"]}`);
                    sessionStorage.setItem('funcId', sketch["funcId"]);
                    sessionStorage.setItem('sidebar', 'todo');
                    sessionStorage.setItem('codeLink', sketch["codeLink"]);
                    sessionStorage.setItem('docLink', sketch["docLink"]);
                    document.location.reload();
                };

                li.appendChild(link);
                ul.appendChild(li);
            });

            details.appendChild(ul);
            nav.appendChild(details);

        });

        return nav;
    }

    function init() {
        let sketchJson = getSketchData()

        const sketchData = JSON.parse(sketchJson)

        nav = document.querySelector('.nav');
        nav.appendChild(getNavigationLinks(sketchData))

        links = Array.from(nav.querySelectorAll('a[href]'));

        btnPrev = document.getElementById('navPrev');
        btnNext = document.getElementById('navNext');
        btnToggle = document.getElementById('toggleSidebar');

        let activeNavId = sessionStorage.getItem('funcId');

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

        const codeLink = sessionStorage.getItem('codeLink');
        if (codeLink) {
            const codeButton = document.getElementById('code-btn');
            if (codeButton) {
                codeButton.disabled = false;
                codeButton.addEventListener('click', (event) => {
                    event.preventDefault();
                    window.open(codeLink, '_blank');
                })
            }
        }

        const docLink = sessionStorage.getItem('docLink');
        if (docLink) {
            const docButton = document.getElementById('doc-btn');
            if (docButton) {
                docButton.disabled = false;
                docButton.addEventListener('click', (event) => {
                    event.preventDefault();
                    window.open(docLink, '_blank');
                })
            }
        }

        const activeSketch = sessionStorage.getItem('funcId');
        if (activeSketch) runSketch(activeSketch);

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