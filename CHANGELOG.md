# Changelog

## 3.3.1

- Replaced XPopup's legacy system UI visibility handling with AndroidX WindowInsets APIs.
- Configured dialog popups for edge-to-edge drawing through WindowCompat and InsetsControllerCompat.
- Added safe-area policy tests for cutouts, side navigation, IME transitions, repeated dispatch, Auto, SafeArea, EdgeToEdge, and RTL physical sides.
- Kept legacy system-bar color APIs binary compatible. Their colors are now rendered by popup-owned protection or scrim content, and the APIs are deprecated for new code.
- Removed XPopup references to legacy system UI visibility flags and FLAG_LAYOUT_NO_LIMITS.
