package kmp.shared.samplecomposemultiplatform.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitViewController
import androidx.compose.ui.window.ComposeUIViewController
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.readValue
import kotlinx.cinterop.useContents
import platform.Foundation.NSSelectorFromString
import platform.Foundation.NSStringFromClass
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIBlurEffect
import platform.UIKit.UIBlurEffectStyle
import platform.UIKit.UIColor
import platform.UIKit.UIDevice
import platform.UIKit.UIEdgeInsetsZero
import platform.UIKit.UIGestureRecognizer
import platform.UIKit.UIImage
import platform.UIKit.UIScreen
import platform.UIKit.UIScrollView
import platform.UIKit.UITabBar
import platform.UIKit.UITabBarAppearance
import platform.UIKit.UITabBarController
import platform.UIKit.UITabBarControllerDelegateProtocol
import platform.UIKit.UITabBarDelegateProtocol
import platform.UIKit.UITabBarItem
import platform.UIKit.UIView
import platform.UIKit.UIViewController
import platform.UIKit.setAdditionalSafeAreaInsets
import platform.UIKit.tabBarItem
import platform.darwin.NSObject
import platform.objc.objc_getClass
import kotlin.random.Random

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PlatformSpecificBottomBar(
    items: List<String>,
    selected: String,
    onSelectedChanged: (String) -> Unit,
    onSizeChanged: (DpSize) -> Unit,
    modifier: Modifier,
) {
    val factory = LocalSampleComposeMultiplatformViewFactory.current
    val key = rememberSaveable { Random.nextInt().toString(16) }
    val density = LocalDensity.current
    val onSizeChangedWithConversion: (Float, Float) -> Unit = { width, height ->
        with(density) { onSizeChanged(DpSize(width.toDp(), height.toDp())) }
    }

    val viewModel = viewModel(key = key) {
        NativeViewHolderViewModel {
            factory.createPlatformSpecificBottomBar(
                items = items,
                selected = selected,
                onSelectedChanged = onSelectedChanged,
                onSizeChanged = onSizeChangedWithConversion,
            )
        }
    }
    val delegate = remember(viewModel) { viewModel.delegate }
    val view = remember(viewModel) { viewModel.view }

    remember(items) { delegate.updateItems(items) }
    remember(selected) { delegate.updateSelected(selected) }
    remember(onSelectedChanged) { delegate.updateOnSelectedChanged(onSelectedChanged) }
    remember(onSizeChanged) { delegate.updateOnSizeChanged(onSizeChangedWithConversion) }
    androidx.compose.ui.viewinterop.UIKitViewController(
        modifier = modifier.fillMaxWidth().height(50.dp).background(Color.Blue),
        factory = { view },
        update = { controller ->
            controller.view.backgroundColor = UIColor.clearColor
            controller.view.opaque = false
            controller.setAdditionalSafeAreaInsets(UIEdgeInsetsZero.readValue())
        },
    )
}

//@OptIn(ExperimentalForeignApi::class)
//@Composable
//actual fun PlatformSpecificBottomBar(
//    items: List<String>,
//    selected: String,
//    onSelectedChanged: (String) -> Unit,
//    onSizeChanged: (DpSize) -> Unit,
//    modifier: Modifier,
//) {
//    val density = LocalDensity.current
//
//    UIKitViewController(
//        factory = {
//            object : UIViewController(nibName = null, bundle = null) {
//                override fun loadView() {
//                    // Root view
//                    view = UIView()
//                    view.backgroundColor = UIColor.clearColor
//                }
//
//                override fun viewDidLoad() {
//                    super.viewDidLoad()
//
//                    // Create tab bar
//                    val tabBar = UITabBar()
//                    tabBar.translatesAutoresizingMaskIntoConstraints = false
//
//                    // Create tab items
//                    tabBar.items = items.mapIndexed { index, title ->
//                        UITabBarItem(title = title, image = null, tag = index.toLong())
//                    }
//
//                    // Set selected item
//                    val selectedIndex = items.indexOf(selected)
//                    if (selectedIndex >= 0) {
//                        tabBar.selectedItem = tabBar.items?.get(selectedIndex) as? UITabBarItem
//                    }
//
//                    // Handle selection changes
//                    tabBar.delegate = object : NSObject(), UITabBarDelegateProtocol {
//                        override fun tabBar(tabBar: UITabBar, didSelectItem: UITabBarItem) {
//                            val index = didSelectItem.tag.toInt()
//                            onSelectedChanged(items[index])
//                        }
//                    }
//
//                    // Add tab bar to root view
//                    view.addSubview(tabBar)
//
//                    // Pin to bottom
//                    NSLayoutConstraint.activateConstraints(
//                        listOf(
//                            tabBar.leadingAnchor.constraintEqualToAnchor(view.leadingAnchor),
//                            tabBar.trailingAnchor.constraintEqualToAnchor(view.trailingAnchor),
//                            tabBar.bottomAnchor.constraintEqualToAnchor(view.bottomAnchor),
//                            tabBar.heightAnchor.constraintEqualToConstant(50.0), // standard tab bar height
//                        ),
//                    )
//
//                    // Report size
//                    view.layoutIfNeeded()
//                    val scale = UIScreen.mainScreen.scale
//                    tabBar.bounds.useContents {
//                        onSizeChanged(
//                            with(density) {
//                                DpSize(
//                                    width = (size.width * scale).toFloat().toDp(),
//                                    height = (size.height * scale).toFloat().toDp(),
//                                )
//                            },
//                        )
//                    }
//                }
//            }
//        },
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(150.dp)
//            .padding(bottom = 100.dp),
//    )
//}

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun ScreenWithPlatformSpecificBottomBar(
    tabs: Map<String, @Composable (() -> Unit)>,
    selectedTab: String,
    onSelectedTabChanged: (String) -> Unit,
    modifier: Modifier,
) {
    val factory = LocalSampleComposeMultiplatformViewFactory.current
    val key = rememberSaveable { Random.nextInt().toString(16) }

    val viewModel = viewModel(key = key) {
        NativeViewHolderViewModel {
            factory.createScreenWithPlatformSpecificBottomBar(
                tabs = tabs.map { (title, composable) ->
                    title to ComposeUIViewController {
                        composable()
                    }
                }.toMap(),
                selectedTab = selectedTab,
                onSelectedTabChanged = onSelectedTabChanged,
            )
        }
    }
    val delegate = remember(viewModel) { viewModel.delegate }
    val view = remember(viewModel) { viewModel.view }

    remember(tabs) {
        delegate.updateTabs(
            tabs.map { (title, composable) ->
                title to ComposeUIViewController {
                    composable()
                }
            }.toMap(),
        )
    }
    remember(selectedTab) { delegate.updateSelectedTab(selectedTab) }
    remember(onSelectedTabChanged) { delegate.updateOnSelectedTabChanged(onSelectedTabChanged) }
    UIKitViewController(
        modifier = modifier.background(Color.Blue),
        factory = { view },
        update = { controller ->
            controller.view.backgroundColor = UIColor.clearColor
            controller.view.opaque = false
            controller.setAdditionalSafeAreaInsets(UIEdgeInsetsZero.readValue())
        },
    )
}

//@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
//@Composable
//actual fun ScreenWithPlatformSpecificBottomBar(
//    tabs: Map<String, @Composable (() -> Unit)>,
//    selectedTab: String,
//    onSelectedTabChanged: (String) -> Unit,
//    modifier: Modifier,
//) {
//    UIKitViewController(
//        modifier = modifier,
//        factory = {
//            object : UITabBarController(nibName = null, bundle = null),
//                UITabBarControllerDelegateProtocol {
//
//                private var composeControllers: List<Pair<String, UIViewController>> = emptyList()
//
//                override fun viewDidLoad() {
//                    super.viewDidLoad()
//                    delegate = this
//
//                    // Transparent / blur tab bar background
//                    if (UIDevice.currentDevice.systemVersion.toDouble() >= 15.0) {
//                        val appearance = UITabBarAppearance()
//                        appearance.configureWithDefaultBackground()
//                        appearance.backgroundEffect = UIBlurEffect.effectWithStyle(
//                            UIBlurEffectStyle.UIBlurEffectStyleSystemMaterial
//                        )
//                        appearance.backgroundColor = UIColor.clearColor
//                        tabBar.standardAppearance = appearance
//                        tabBar.scrollEdgeAppearance = appearance
//                    } else {
//                        tabBar.barTintColor = UIColor.clearColor
//                        tabBar.translucent = true
//                    }
//
//                    // Build controllers for each tab
//                    composeControllers = tabs.map { (key, content) ->
//                        val vc = ComposeUIViewController { content() }
//
//                        // Optional syntax "Title::iconName"
//                        val parts = key.split("::")
//                        val title = parts[0]
//                        val iconName = parts.getOrNull(1)
//
//                        val item = if (iconName != null) {
//                            UITabBarItem(
//                                title = title,
//                                image = UIImage.systemImageNamed(iconName),
//                                tag = 0
//                            )
//                        } else {
//                            UITabBarItem(title = title, image = null, tag = 0)
//                        }
//
//                        vc.tabBarItem = item
//                        title to vc
//                    }
//
//                    setViewControllers(composeControllers.map { it.second }, animated = false)
//
//                    // Initial selection
//                    val idx = composeControllers.indexOfFirst { it.first == selectedTab }
//                    if (idx >= 0) selectedIndex = idx.toULong()
//
//                    // 🔧 Disable vertical scrolling only in Compose views
//                    composeControllers.forEach { (_, vc) ->
//                        disableComposeScroll(vc.view)
//                    }
//                }
//
//                // Handle tab selection
//                override fun tabBarController(
//                    tabBarController: UITabBarController,
//                    didSelectViewController: UIViewController
//                ) {
//                    val match = composeControllers.firstOrNull { it.second == didSelectViewController }
//                    match?.let { onSelectedTabChanged(it.first) }
//                }
//
//                // React to external selection changes
//                fun updateSelectedTab(tab: String) {
//                    val idx = composeControllers.indexOfFirst { it.first == tab }
//                    if (idx >= 0 && idx.toULong() != selectedIndex) {
//                        selectedIndex = idx.toULong()
//                    }
//                }
//
//                /**
//                 * Disable vertical scrolling in Compose UIScrollViews only,
//                 * leaving UITabBar gestures intact.
//                 */
//                private fun disableComposeScroll(root: UIView?) {
//                    root ?: return
//                    val count = root.subviews.size
//                    for (i in 0 until count) {
//                        val child = root.subviews[i] as? UIView ?: continue
//
//                        // Only target Compose scroll views
//                        val className = NSStringFromClass(child.`class`() ?: return) ?: ""
//                        if (className.contains("UIScrollView") && className.contains("Compose")) {
//                            val scroll = child as UIScrollView
//                            scroll.bounces = false
//                            scroll.alwaysBounceVertical = false
//                            scroll.scrollEnabled = false
//                            // ⚠️ Do NOT remove gesture recognizers
//                        }
//
//                        // Recurse into children
//                        disableComposeScroll(child)
//                    }
//                }
//            }
//        },
//    )
//}