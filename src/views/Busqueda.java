package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import factorys.DBConnection;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	private static JTable activeTable;
	private static String idEditar;// id obtenida de la fila seleccionada con la que editar reserva/huesped

	public static String getIdEditar() {
		return idEditar;
	}

	public static void setIdEditar(String idEditar) {
		Busqueda.idEditar = idEditar;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		
		
		
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Numero de Reserva");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		JScrollPane scroll_table = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);
		tbReservas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Obtener la fila seleccionada
            	int filaSeleccionada = tbReservas.getSelectedRow();
            	activeTable = tbReservas;

            }
        });
		
		

		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Número de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("Telefono");
		modeloHuesped.addColumn("Número de Reserva");
		JScrollPane scroll_tableHuespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		tbHuespedes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Obtener la fila seleccionada
            	int filaSeleccionada = tbHuespedes.getSelectedRow();
            	activeTable = tbHuespedes;

            }
        });

		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { //Al usuario pasar el mouse por el botón este cambiará de color
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { //Al usuario quitar el mouse por el botón este volverá al estado original
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO 
				// if -> puros numeros -> buscar por id , else -> letras-> buscar por Apellido
				// popular la tabla con los resultados, hacerla seleccionable?
				String textoBuscar = txtBuscar.getText();
				if( !textoBuscar.isEmpty()) {
					Buscar(modelo,modeloHuesped,textoBuscar);
				}
				
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		lblEditar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = activeTable.getSelectedRow();

                if (filaSeleccionada != -1) {
                	
                	setIdEditar(activeTable.getModel().getValueAt(filaSeleccionada, 0).toString());
                   if(activeTable.getModel().getColumnCount() == 5) {
                	   // se clickeo en tabla reserva, se envia la idReserva en ambos casos 
                	   ReservasView rv = new ReservasView();
                	   rv.addWindowListener(new WindowAdapter() {
                           @Override
                           public void windowClosed(WindowEvent e) {
                               // Aquí se ejecuta el código después de que el JFrame se cierre
                        	   Buscar(modelo,modeloHuesped,idEditar);// carga las tablas con la nueva info
                        	   setIdEditar("");
                           }
                       });
                	   rv.setVisible(true);
                	   
                   }
                   else {
                	   RegistroHuesped rh = new RegistroHuesped();
                	   rh.addWindowListener(new WindowAdapter() {
                           @Override
                           public void windowClosed(WindowEvent e) {
                               // Aquí se ejecuta el código después de que el JFrame se cierre
                        	   Buscar(modelo,modeloHuesped,idEditar);
                        	   setIdEditar("");
                           }
                       });
                	   rh.setVisible(true);
                	   
                   }
         
                } 
                
            }
        });

		
		
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
		
		lblEliminar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = activeTable.getSelectedRow();

                if (filaSeleccionada != -1) {
                	 // se clickeo en tabla reserva, se envia la idReserva en ambos casos 
                	setIdEditar(activeTable.getModel().getValueAt(filaSeleccionada, 0).toString());
                	// sql para eliminar
                	if(activeTable.getModel().getColumnCount() == 5) {
                		Eliminar("DELETE from hotel_alura.reservas where id=?");
                		((DefaultTableModel) activeTable.getModel()).removeRow(filaSeleccionada);
                		((AbstractTableModel) activeTable.getModel()).fireTableDataChanged(); 
                	}
                	else {
                		Eliminar("DELETE from hotel_alura.huespedes where id=?");
                		((DefaultTableModel) activeTable.getModel()).removeRow(filaSeleccionada);
                		((AbstractTableModel) activeTable.getModel()).fireTableDataChanged(); 
                	}
                	
                	// borrar fila
        
                } 
                
            }
        });
	}
	
//Código que permite mover la ventana por la pantalla según la posición de "x" y "y"
	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
}
	    private void Buscar(DefaultTableModel modelo,DefaultTableModel modeloHuesped,String textoBuscar) {

	    	 Connection con = null;
	    	 String consulta ="";
	    	 
	    	 if(textoBuscar.matches("\\d+")) {
	    		 // solo numeros
	    		 consulta = "Select r.*,h.* from hotel_alura.reservas as r "
					 		+ "inner join hotel_alura.huespedes as h where r.id = h.IdReserva and r.id=?";
	    	 }
	    	 else if(textoBuscar.matches("[a-zA-Z]+")) {
	    		 //solo letras
	    		 consulta = "Select r.*,h.* from hotel_alura.reservas as r "
					 		+ "inner join hotel_alura.huespedes as h where r.id = h.IdReserva and h.apellido=?";
	    		 
	    	 }
	    		 try {
	    			 con = new DBConnection().resolverConeccion();
	    			 PreparedStatement  preparedStatement = 
	    					 con.prepareStatement(consulta);
	    			 preparedStatement.setString(1, textoBuscar);
	    			 ResultSet resultado = preparedStatement.executeQuery();
	    			 // !resultado.isBeforeFirst()   revisa que no hayan filas afectadas sin avanzar a la siguiente fila como lo haría next();
	    			 
	    			 // vacia las filas antes de agregar lo que resulte de la nueva consulta, tambien evita duplicados en caso de apretar varias veces el boton con un mismo texto en la busqueda
    				 modelo.setRowCount(0);
    				 modeloHuesped.setRowCount(0);
	    			 
	    				 
	    			 
	    			 while( resultado.next()) {
	    				 
	    				 Object[] rowDataR = {
	    	                        resultado.getObject("r.Id"),
	    	                        resultado.getObject("r.FechaEntrada"),
	    	                        resultado.getObject("r.FechaSalida"),
	    	                        resultado.getObject("r.Valor"),
	    	                        resultado.getObject("r.FormaPago")
	    	                };
	    	                modelo.addRow(rowDataR);
	    	                
	    	                Object[] rowDataH = {
	    	                        resultado.getObject("h.Id"),
	    	                        resultado.getObject("h.Nombre"),
	    	                        resultado.getObject("h.Apellido"),
	    	                        resultado.getObject("h.FechaDeNacimiento"),
	    	                        resultado.getObject("h.Nacionalidad"),
	    	                        resultado.getObject("h.Telefono"),
	    	                        resultado.getObject("h.IdReserva"),
	    	                };
	    	                modeloHuesped.addRow(rowDataH);
	    			 }
	    			 resultado.close();
	    			 modelo.fireTableDataChanged();
	    			 modeloHuesped.fireTableDataChanged();
	    			 
				} catch (Exception e) {
					// TODO: handle exception
					 e.printStackTrace();
				}
	    	 }
	    

	    private void Eliminar(String sql) {
	    	// hacer un drop cascade con la id que llegue
	    	Connection con = null;
	    	try {
	    		con = new DBConnection().resolverConeccion();
	    	    PreparedStatement preparedStatement1 = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    	    preparedStatement1.setString(1, Busqueda.getIdEditar());
	    	    preparedStatement1.executeUpdate();
    
	    	    
			} catch (SQLException e) {
                // Manejar cualquier excepción que pueda ocurrir al cerrar la conexión
                e.printStackTrace();
            }
	    	finally {
	    		try {
	                // Cerrar la conexión en el bloque finally
	                if (con != null) {
	                    con.close();
	                    
	                    
	                    
	                }
	            } catch (SQLException e) {
	                // Manejar cualquier excepción que pueda ocurrir al cerrar la conexión
	                e.printStackTrace();
	            }
	    	}
	    	
	    	
	    	
	    	// al final 
	    	setIdEditar("");
	    }
}



	
	    	 
	    

