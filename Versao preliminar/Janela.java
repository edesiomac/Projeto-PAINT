import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;

public class Janela extends JFrame 
{
    protected static final long serialVersionUID = 1L;

    protected JButton btnPonto   = new JButton ("Ponto"),
                      btnLinha   = new JButton ("Linha"),
                      btnCirculo = new JButton ("Circulo"),
                      btnElipse  = new JButton ("Elipse"),
                      btnCores   = new JButton ("Cores"),
                      btnAbrir   = new JButton ("Abrir"),
                      btnSalvar  = new JButton ("Salvar"),
                      btnApagar  = new JButton ("Apagar"),
                      btnSair    = new JButton ("Sair");

    protected MeuJPanel pnlDesenho = new MeuJPanel ();
    
    protected JLabel statusBar1 = new JLabel ("Mensagem:"),
                     statusBar2 = new JLabel ("Coordenada:");
    

    protected boolean esperaPonto, esperaInicioReta, esperaFimReta, esperaInicioCirculo,
    				  esperaFimCirculo, esperaInicioElipse, esperaFimElipse;

    protected Color corAtual = Color.BLACK;
    protected Ponto p1;
    protected Circulo circulo;
    protected Elipse elipse;
    
    protected Vector<Figura> figuras = new Vector<Figura>();

    public Janela ()
    {
        super("Editor Gráfico");

        try
        {
            Image btnPontoImg = ImageIO.read(getClass().getResource("ponto.jpg"));
            btnPonto.setIcon(new ImageIcon(btnPontoImg));
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo ponto.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try
        {
            Image btnLinhaImg = ImageIO.read(getClass().getResource("linha.jpg"));
            btnLinha.setIcon(new ImageIcon(btnLinhaImg));
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo linha.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try
        {
            Image btnCirculoImg = ImageIO.read(getClass().getResource("circulo.jpg"));
            btnCirculo.setIcon(new ImageIcon(btnCirculoImg));
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo circulo.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try
        {
            Image btnElipseImg = ImageIO.read(getClass().getResource("elipse.jpg"));
            btnElipse.setIcon(new ImageIcon(btnElipseImg));
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo elipse.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try
        {
            Image btnCoresImg = ImageIO.read(getClass().getResource("cores.jpg"));
            btnCores.setIcon(new ImageIcon(btnCoresImg));
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo cores.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try
        {
            Image btnAbrirImg = ImageIO.read(getClass().getResource("abrir.jpg"));
            btnAbrir.setIcon(new ImageIcon(btnAbrirImg));
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo abrir.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try
        {
            Image btnSalvarImg = ImageIO.read(getClass().getResource("salvar.jpg"));
            btnSalvar.setIcon(new ImageIcon(btnSalvarImg));
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo salvar.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try
        {
            Image btnApagarImg = ImageIO.read(getClass().getResource("apagar.jpg"));
            btnApagar.setIcon(new ImageIcon(btnApagarImg));
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo apagar.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        try
        {
            Image btnSairImg = ImageIO.read(getClass().getResource("sair.jpg"));
            btnSair.setIcon(new ImageIcon(btnSairImg));
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog (null,
                                           "Arquivo sair.jpg não foi encontrado",
                                           "Arquivo de imagem ausente",
                                           JOptionPane.WARNING_MESSAGE);
        }

        btnPonto.addActionListener (new DesenhoDePonto());
        btnLinha.addActionListener (new DesenhoDeReta ());
        btnCirculo.addActionListener(new DesenhoDeCirculo());
        btnElipse.addActionListener(new DesenhoDeElipse());
        btnCores.addActionListener(new SelecionarCorDesenho());
        btnSalvar.addActionListener(new SalvarDesenho());
        btnAbrir.addActionListener(new AbrirDesenho());
        btnSair.addActionListener(new SairDesenho());
        btnApagar.addActionListener(new ApagarDesenho());
        
        

        JPanel     pnlBotoes = new JPanel();
        FlowLayout flwBotoes = new FlowLayout(); 
        pnlBotoes.setLayout (flwBotoes);

        pnlBotoes.add (btnAbrir);
        pnlBotoes.add (btnSalvar);
        pnlBotoes.add (btnPonto);
        pnlBotoes.add (btnLinha);
        pnlBotoes.add (btnCirculo);
        pnlBotoes.add (btnElipse);
        pnlBotoes.add (btnCores);
        pnlBotoes.add (btnApagar);
        pnlBotoes.add (btnSair);

        JPanel     pnlStatus = new JPanel();
        GridLayout grdStatus = new GridLayout(1,2);
        pnlStatus.setLayout(grdStatus);

        pnlStatus.add(statusBar1);
        pnlStatus.add(statusBar2);

        Container cntForm = this.getContentPane();
        cntForm.setLayout (new BorderLayout());
        cntForm.add (pnlBotoes,  BorderLayout.NORTH);
        cntForm.add (pnlDesenho, BorderLayout.CENTER);
        cntForm.add (pnlStatus,  BorderLayout.SOUTH);
        
        this.addWindowListener (new FechamentoDeJanela());

        this.setSize (700,500);
        this.setVisible (true);
    }

    protected class MeuJPanel extends    JPanel 
                              implements MouseListener,
                                         MouseMotionListener
    {
	public MeuJPanel()
        {
            super();

            this.addMouseListener       (this);
            this.addMouseMotionListener (this);
        }

        public void paint (Graphics g)
        {
            for (int i=0 ; i<figuras.size(); i++)
                figuras.get(i).torneSeVisivel(g);
        }
        
        public void mousePressed (MouseEvent e)
        {
            if (esperaPonto)
            {
                figuras.add (new Ponto (e.getX(), e.getY(), corAtual));
                figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                esperaPonto = false;
            }
            else
                if (esperaInicioReta)
                {
                    p1 = new Ponto (e.getX(), e.getY(), corAtual);
                    esperaInicioReta = false;
                    esperaFimReta = true;
                    statusBar1.setText("Mensagem: clique o ponto final da reta");    
                 }
                 else
                    if (esperaFimReta)
                    {
                        esperaInicioReta = false;
                        esperaFimReta = false;
                        figuras.add (new Linha(p1.getX(), p1.getY(), e.getX(), e.getY(), corAtual));
                        figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                        statusBar1.setText("Mensagem:");    
                    }
            		
            		if(esperaInicioCirculo)
            		{
            			p1 = new Ponto (e.getX(), e.getY(), corAtual);
                        esperaInicioReta = false;
                        esperaFimReta = true;
                        statusBar1.setText("Mensagem: clique o ponto final da reta");  
            		}
            		else 
            			if(esperaFimCirculo){
            			
            			esperaInicioReta = false;
                        esperaFimReta = false;
                        double raio  = Math.sqrt(Math.pow(e.getX() - p1.getX(), 2) + Math.pow(e.getY() - p1.getY(), 2));
                        figuras.add (new Linha(p1.getX(), p1.getY(), e.getX(), e.getY(), corAtual));
                        figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                        statusBar1.setText("Mensagem:");    
            		}
            		if(esperaInicioElipse){
            			p1 = new Ponto (e.getX(), e.getY(), corAtual);
                        esperaInicioReta = false;
                        esperaFimReta = true;
                        statusBar1.setText("Mensagem: clique o ponto final da reta"); 
            		}
            		else if(esperaFimElipse){
            			esperaInicioReta = false;
                        esperaFimReta = false;
                        figuras.add (new Linha(p1.getX(), p1.getY(), e.getX(), e.getY(), corAtual));
                        figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
                        statusBar1.setText("Mensagem:");    
            		}
        }
        
        public void mouseReleased (MouseEvent e)
        {}
        
        public void mouseClicked (MouseEvent e)
        {}
        
        public void mouseEntered (MouseEvent e)
        {}

        public void mouseExited (MouseEvent e)
        {}
        
        public void mouseDragged(MouseEvent e)
        {}

        public void mouseMoved(MouseEvent e)
        {
            statusBar2.setText("Coordenada: "+e.getX()+","+e.getY());
        }
    }

    protected class DesenhoDePonto implements ActionListener
    {
          public void actionPerformed (ActionEvent e)    
          {
              esperaPonto      = true;
              esperaInicioReta = false;
              esperaFimReta    = false;
              esperaInicioCirculo = false;
			  esperaFimCirculo = false;
			  esperaInicioElipse = false;
			  esperaFimElipse = false;

              statusBar1.setText("Mensagem: clique o local do ponto desejado");
          }
    }

    protected class DesenhoDeReta implements ActionListener
    {
        public void actionPerformed (ActionEvent e)    
        {
            esperaPonto      = false;
            esperaInicioReta = true;
            esperaFimReta    = false;
            esperaInicioCirculo = false;
			esperaFimCirculo = false;
			esperaInicioElipse = false;
			esperaFimElipse = false;

            statusBar1.setText("Mensagem: clique o ponto inicial da reta");
        }
    }
    
    protected class DesenhoDeCirculo implements ActionListener{
    	
    	public void actionPerformed(ActionEvent e){
    		
    		esperaPonto      = false;
            esperaInicioReta = false;
            esperaFimReta    = false;
            esperaInicioCirculo = true;
			esperaFimCirculo = false;
			esperaInicioElipse = false;
			esperaFimElipse = false;
    		
    	}
    }
    
    protected class DesenhoDeElipse implements ActionListener{
    	
    	public void actionPerformed(ActionEvent e){
    		
    		esperaPonto      = false;
            esperaInicioReta = false;
            esperaFimReta    = false;
            esperaInicioCirculo = false;
			esperaFimCirculo = false;
			esperaInicioElipse = true;
			esperaFimElipse = false;
    		
    	}
    }
    
    protected class SelecionarCorDesenho implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{
			corAtual = JColorChooser.showDialog(Janela.this, "Escolher a color", corAtual);
			if(corAtual == null)
				corAtual = Color.black;
		}	
	}
    
    protected class SalvarDesenho implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{		
			try
			{
				JFileChooser caminho = new JFileChooser();
				caminho.setCurrentDirectory(new java.io.File("C:/Users/Edésio/Desktop"));
				caminho.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if(caminho.showSaveDialog(btnSalvar) == JFileChooser.APPROVE_OPTION)
				{
					String path = caminho.getSelectedFile().getAbsolutePath();
					String nomeArquivo = caminho.getSelectedFile().getName();
					PrintWriter gravador = new PrintWriter(new FileWriter (path + ".txt"));
					gravador.flush();
					for(int i = 0; i < figuras.size(); i++)
					{
						gravador.println(figuras.get(i).toString());
					}
					gravador.close();
				}
				else if(caminho.showOpenDialog(btnSalvar) == JFileChooser.CANCEL_OPTION)
				{
					
				}
			}
			catch(Exception err)
			{
				System.out.println("Não foi possível salvar o arquivo");
				err.printStackTrace();
			}
		}	
	}
    
    protected class AbrirDesenho implements ActionListener
	{
		public void actionPerformed (ActionEvent e)
		{						
			try
			{
				figuras = new Vector<Figura>();
				JFileChooser caminho = new JFileChooser();
				caminho.setCurrentDirectory(new java.io.File("C:/Users/Edésio/Desktop"));
				caminho.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				if(caminho.showOpenDialog(btnAbrir) == JFileChooser.APPROVE_OPTION)
				{
					File desenho = caminho.getSelectedFile();
					BufferedReader leitor = new BufferedReader(new FileReader(desenho));
					String figura = null;
					char tipo;
					while(leitor.ready())
					{
						figura = leitor.readLine();
					
						if(figura.charAt(0) == 'e')
						{
							figuras.add(new Elipse(figura));
							figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
						}
						else if(figura.charAt(0) == 'c')
						{
							figuras.add(new Circulo(figura));
							figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
						}
						else if(figura.charAt(0) == 'r')
						{
							figuras.add(new Linha(figura));
							figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
						}
						else if(figura.charAt(0) == 'p')
						{
							figuras.add(new Ponto(figura));
							figuras.get(figuras.size()-1).torneSeVisivel(pnlDesenho.getGraphics());
						}
					}
					leitor.close();
				}
				else if(caminho.showOpenDialog(btnAbrir) == JFileChooser.CANCEL_OPTION)
				{
					
				}
			}
			catch(Exception err)
			{
				System.out.println("Não foi possível ler o arquivo");
				err.printStackTrace();
			}
		}	
	}
    
    protected class ApagarDesenho implements ActionListener
    {
            public void actionPerformed(ActionEvent e) {
            	
            	
                
    	}
    }
    
    protected class SairDesenho implements ActionListener
    {
    	
    	public void actionPerformed(ActionEvent e){
    		
    		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    		
    		if (JOptionPane.showConfirmDialog(null,"Deseja sair")==JOptionPane.OK_OPTION){
				System.exit(0);
				}
    		
    	}
    }
 

    protected class FechamentoDeJanela extends WindowAdapter
    {
        public void windowClosing (WindowEvent e)
        {
            System.exit(0);
        }
    }
}
