import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class SampleServer {
	Server jetty;

	private void initWebUI() throws Exception {
		jetty = new Server(8080);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/admin");
		jetty.setHandler(context);

		ServletHolder loader = new ServletHolder(new com.vaadin.terminal.gwt.server.ApplicationServlet());
		loader.setInitParameter("productionMode", "false");
		loader.setInitParameter("application", "webui.AdminApplication");
		context.addServlet(loader, "/*");
		ContextHandlerCollection contexts = new ContextHandlerCollection();
		contexts.setHandlers(new Handler[] { context });
		jetty.setHandler(contexts);
		jetty.start();
	}

	private void startup() throws Exception {
		initWebUI();
	}

	public static void main(String[] args) throws Exception {
		new SampleServer().startup();
	}

}
