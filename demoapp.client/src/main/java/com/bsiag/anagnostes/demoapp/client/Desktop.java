package com.bsiag.anagnostes.demoapp.client;

import java.util.List;

import org.eclipse.scout.rt.client.session.ClientSessionProvider;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktop;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;
import org.eclipse.scout.rt.client.ui.form.ScoutInfoForm;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;

import com.bsiag.anagnostes.demoapp.client.search.SearchOutline;
import com.bsiag.anagnostes.demoapp.client.settings.SettingsOutline;
import com.bsiag.anagnostes.demoapp.client.work.WorkOutline;
import com.bsiag.anagnostes.demoapp.shared.Icons;

/**
 * <h3>{@link Desktop}</h3>
 *
 * @author cbu
 */
public class Desktop extends AbstractDesktop {
	@Override
	protected String getConfiguredTitle() {
		return TEXTS.get("ApplicationTitle");
	}

	@Override
	protected String getConfiguredLogoId() {
		return Icons.AppLogo;
	}

	@Override
	protected List<Class<? extends IOutline>> getConfiguredOutlines() {
		return CollectionUtility.<Class<? extends IOutline>>arrayList(WorkOutline.class, SearchOutline.class,
				SettingsOutline.class);
	}

	@Override
	protected void execDefaultView() {
		selectFirstVisibleOutline();
	}

	protected void selectFirstVisibleOutline() {
		for (IOutline outline : getAvailableOutlines()) {
			if (outline.isEnabled() && outline.isVisible()) {
				setOutline(outline.getClass());
				return;
			}
		}
	}

	@Override
	protected void execOutlineChanged(IOutline oldOutline, IOutline newOutline) {
		if (newOutline instanceof WorkOutline && newOutline.getActivePage() == null) {
			activateFirstPage();
		}
	}
	
	@Order(1000)
	public class FileMenu extends AbstractMenu {

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("File");
		}

		@Order(1000)
		public class ExitMenu extends AbstractMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("Exit");
			}

			@Override
			protected void execAction() {
				ClientSessionProvider.currentSession(ClientSession.class).stop();
			}
		}
	}

	@Order(2000)
	public class HelpMenu extends AbstractMenu {

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Help");
		}

		@Order(1000)
		public class AboutMenu extends AbstractMenu {

			@Override
			protected String getConfiguredText() {
				return TEXTS.get("About");
			}

			@Override
			protected void execAction() {
				ScoutInfoForm form = new ScoutInfoForm();
				form.startModify();
			}
		}
	}

	@Order(1000)
	public class WorkOutlineViewButton extends AbstractOutlineViewButton {

		public WorkOutlineViewButton() {
			this(WorkOutline.class);
		}

		protected WorkOutlineViewButton(Class<? extends WorkOutline> outlineClass) {
			super(Desktop.this, outlineClass);
		}

		@Override
		protected String getConfiguredKeyStroke() {
			return IKeyStroke.F2;
		}
	}

	@Order(2000)
	public class SearchOutlineViewButton extends AbstractOutlineViewButton {

		public SearchOutlineViewButton() {
			this(SearchOutline.class);
		}

		protected SearchOutlineViewButton(Class<? extends SearchOutline> outlineClass) {
			super(Desktop.this, outlineClass);
		}

		@Override
		protected DisplayStyle getConfiguredDisplayStyle() {
			return DisplayStyle.TAB;
		}

		@Override
		protected String getConfiguredKeyStroke() {
			return IKeyStroke.F3;
		}
	}

	@Order(3000)
	public class SettingsOutlineViewButton extends AbstractOutlineViewButton {

		public SettingsOutlineViewButton() {
			this(SettingsOutline.class);
		}

		protected SettingsOutlineViewButton(Class<? extends SettingsOutline> outlineClass) {
			super(Desktop.this, outlineClass);
		}

		@Override
		protected DisplayStyle getConfiguredDisplayStyle() {
			return DisplayStyle.TAB;
		}

		@Override
		protected String getConfiguredKeyStroke() {
			return IKeyStroke.F10;
		}
	}
}
