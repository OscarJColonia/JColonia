package examenFINAL;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class AgendaMóvil {

	private JFrame frmAgendaMóvil;
	private JPanel panelExterior;
	private JPanel panelEtiqueta;
	private JPanel panelBorde;
	private JScrollPane panelDeslizante;
	private JTable tablaDatos;
	private JButton botónCargar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AgendaMóvil window = new AgendaMóvil();
					window.frmAgendaMóvil.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AgendaMóvil() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAgendaMóvil = new JFrame();
		frmAgendaMóvil.setTitle("Agenda móvil");
		frmAgendaMóvil.setBounds(100, 100, 450, 300);
		frmAgendaMóvil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAgendaMóvil.getContentPane().add(getPanelExterior(), BorderLayout.CENTER);
	}

	private JPanel getPanelExterior() {
		if (panelExterior == null) {
			panelExterior = new JPanel();
			panelExterior.setBorder(new EmptyBorder(10, 10, 10, 10));
			panelExterior.setLayout(new BorderLayout(10, 10));
			panelExterior.add(getPanelEtiqueta(), BorderLayout.CENTER);
			panelExterior.add(getBotónCargar(), BorderLayout.SOUTH);
		}
		return panelExterior;
	}

	private JPanel getPanelEtiqueta() {
		if (panelEtiqueta == null) {
			panelEtiqueta = new JPanel();
			panelEtiqueta
					.setBorder(new TitledBorder(null, "Datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelEtiqueta.setLayout(new BorderLayout(0, 0));
			panelEtiqueta.add(getPanelBorde(), BorderLayout.CENTER);
		}
		return panelEtiqueta;
	}

	private JPanel getPanelBorde() {
		if (panelBorde == null) {
			panelBorde = new JPanel();
			panelBorde.setBorder(new EmptyBorder(10, 10, 10, 10));
			panelBorde.setLayout(new BorderLayout(10, 10));
			panelBorde.add(getPanelDeslizante(), BorderLayout.CENTER);
		}
		return panelBorde;
	}

	private JScrollPane getPanelDeslizante() {
		if (panelDeslizante == null) {
			panelDeslizante = new JScrollPane();
			panelDeslizante.setViewportView(getTablaDatos());
		}
		return panelDeslizante;
	}

	private JTable getTablaDatos() {
		if (tablaDatos == null) {
			tablaDatos = new JTable(
					new DefaultTableModel(new Object[][] {}, new String[] { "#", "Nombre", "Teléfono" }) {
						private static final long serialVersionUID = 20210601001L;
						Class<?>[] columnTypes = new Class[] { String.class, String.class, String.class };

						public Class<?> getColumnClass(int columnIndex) {
							return columnTypes[columnIndex];
						}
					});

		}
		return tablaDatos;
	}

	private JButton getBotónCargar() {
		if (botónCargar == null) {
			botónCargar = new JButton("Cargar datos");
			botónCargar.setName("botónCargar");
			botónCargar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evento) {
					String posición, nombre, teléfono;

					int i = 0;

					try (AccesoBD bd = new AccesoBD()) { // Cierre implícito con close()
						bd.abrirConexión();
						Vector<ContactoMóvil> lista = new Vector<ContactoMóvil>();
						bd.leer(lista);
						for (ContactoMóvil contacto : lista) {
							posición = String.format("%d", ++i);
							nombre = contacto.getNombre();
							teléfono = contacto.toString();
							añadirFila(posición, nombre, teléfono);

						}
					} catch (AccesoBDException e) {
						// TODO Hacer algo «más políticamente correcto»
						e.printStackTrace();
					}

				}
			});
			botónCargar.setMnemonic('A');
		}
		return botónCargar;
	}

	private void añadirFila(String posición, String nombre, String teléfono) {
		Vector<String> nuevaFila;
		DefaultTableModel modelo = (DefaultTableModel) getTablaDatos().getModel();
		nuevaFila = new Vector<String>(modelo.getColumnCount());
		nuevaFila.add(posición);
		nuevaFila.add(nombre);
		nuevaFila.add(teléfono);

		modelo.addRow(nuevaFila);
	}

	private void vaciarTabla() {
		DefaultTableModel modelo = (DefaultTableModel) getTablaDatos().getModel();
		modelo.setRowCount(0);
	}
}
