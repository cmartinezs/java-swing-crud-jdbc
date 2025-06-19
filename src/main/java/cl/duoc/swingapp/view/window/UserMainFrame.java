package cl.duoc.swingapp.view.window;

import cl.duoc.swingapp.view.entity.UserView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class UserMainFrame extends JFrame {
  private final DefaultTableModel tableModel;
  private final JTable table;
  private final JButton btnNew;
  private final JButton btnEdit;
  private final JButton btnDelete;

  public UserMainFrame() {
    super("CRUD Usuario");
    tableModel = new DefaultTableModel(new Object[] {"ID", "Nombre", "Email"}, 0);
    table = new JTable(tableModel);
    btnNew = new JButton("Nuevo");
    btnEdit = new JButton("Editar");
    btnDelete = new JButton("Borrar");

    JPanel buttons = new JPanel();
    buttons.add(btnNew);
    buttons.add(btnEdit);
    buttons.add(btnDelete);

    setLayout(new BorderLayout());
    add(new JScrollPane(table), BorderLayout.CENTER);
    add(buttons, BorderLayout.SOUTH);

    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(600, 400);
    setLocationRelativeTo(null);
  }

  public void addNewUserListener(ActionListener l) {
    btnNew.addActionListener(l);
  }

  public void addEditUserListener(ActionListener l) {
    btnEdit.addActionListener(l);
  }

  public void addDeleteUserListener(ActionListener l) {
    btnDelete.addActionListener(l);
  }

  public Optional<Long> getSelectedUserId() {
    int row = table.getSelectedRow();
    return row < 0 ? Optional.empty() : Optional.of((Long) tableModel.getValueAt(row, 0));
  }

  public void setTableData(List<UserView> users) {
    tableModel.setRowCount(0);
    for (UserView u : users) {
      tableModel.addRow(new Object[]{u.getId(), u.getUsername(), u.getEmail()});
    }
  }

  public void showError(String msg) {
    JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
  }
}
